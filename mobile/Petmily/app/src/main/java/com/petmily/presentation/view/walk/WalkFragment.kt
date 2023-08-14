package com.petmily.presentation.view.walk

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentWalkBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.util.WalkWorker

private const val TAG = "Fetmily_WalkFragment"
class WalkFragment : BaseFragment<FragmentWalkBinding>(FragmentWalkBinding::bind, R.layout.fragment_walk) {

    private val mainActivity by lazy { context as MainActivity }

    private lateinit var workManager: WorkManager
    private lateinit var workRequest: WorkRequest

    private var isPermissionChecked = false
    private var startWalk = false

    private val locationManager by lazy {
        // ~~Manager는 getSystemService로 호출
        mainActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClick()
        checkPermission()
    }

    private fun initView() = with(binding) {
        if (ApplicationClass.sharedPreferences.isWalking()) {
            btnWalkStart.text = "산책 종료!"
        } else {
            btnWalkStart.text = "산책 시작!"
        }
    }

    private fun initClick() = with(binding) {
        // 산책 시작/종료 버튼
        btnWalkStart.setOnClickListener {
            if (ApplicationClass.sharedPreferences.isWalking()) {
                // 산책중일 경우 산책 종료
                stopWalk()
            } else if (isPermissionChecked && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // 산책중이지 않으며 권한이 적용되어 있을 경우
                startWalk()
            } else {
                checkPermission()
            }
        }
    }

    // 권한 체크
    private fun checkPermission() {
        workManager = WorkManager.getInstance(mainActivity)
        workRequest = OneTimeWorkRequestBuilder<WalkWorker>().build()

        val permissionListener = object : PermissionListener {
            // 권한 얻기에 성공했을 때 동작 처리
            override fun onPermissionGranted() {
                Log.d(TAG, "onPermissionGranted: 권한 받았음")

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }

                isPermissionChecked = true
            }

            // 권한 얻기에 실패했을 때 동작 처리
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                isPermissionChecked = false
                mainActivity.showSnackbar("위치 권한이 거부되었습니다.")
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setDeniedMessage("[설정] 에서 위치 접근 권한을 부여해야만 사용이 가능합니다.")
            // 필요한 권한 설정
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.POST_NOTIFICATIONS,
            )
            .check()
    }

    // 산책 종료
    private fun stopWalk() {
        ApplicationClass.sharedPreferences.stopWalk()

        binding.btnWalkStart.text = "산책 시작!"

        workManager.cancelAllWork()
    }

    @SuppressLint("RestrictedApi")
    private fun startWalk() {
        ApplicationClass.sharedPreferences.startWalk()

        binding.btnWalkStart.text = "산책 종료!"

        MainActivity.apply {
            walkDist = 0f
            walkTime = 0
        }

        workManager = WorkManager.getInstance(mainActivity)
        workRequest = OneTimeWorkRequestBuilder<WalkWorker>().build()

        workManager.enqueue(workRequest)

        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(
                mainActivity,
                Observer { workInfo ->
                    if (workInfo != null) {
                        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                            // Work completed successfully
//                                    binding.tvLocation.text = workInfo.outputData.getString("location") ?: "fail"
                            Log.d(TAG, "onCreate: success")
                        } else if (workInfo.state == WorkInfo.State.FAILED) {
                            // Work failed
                            Log.d(TAG, "onCreate: fail")
                        } else if (workInfo.state == WorkInfo.State.CANCELLED) {
                            // Work cancelled
//                                    binding.tvLocation.text = workInfo.outputData.getString("location") ?: "fail"
                            Log.d(TAG, "onCreate: cancelled ${MainActivity.walkDist}")
                            mainActivity.showSnackbar("${MainActivity.walkDist} / ${MainActivity.walkTime}")
                        }
                        // You can also access other properties of workInfo like
                        // workInfo.progress to track progress if you update it in the worker
                    }
                },
            )
    }
}
