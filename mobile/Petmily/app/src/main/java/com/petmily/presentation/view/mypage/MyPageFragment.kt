package com.petmily.presentation.view.mypage

import android.content.Context
import android.os.Bundle
import android.system.Os.bind
import android.util.Log
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.circleCrop
import com.google.android.material.tabs.TabLayout
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentMyPageBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.view.curation.CurationAdapter
import com.petmily.presentation.view.dialog.LogoutDialog
import com.petmily.presentation.view.dialog.WithDrawalDialog
import com.petmily.presentation.view.home.BoardAdapter
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.presentation.viewmodel.PetViewModel
import com.petmily.presentation.viewmodel.UserViewModel
import com.petmily.repository.dto.Board
import com.petmily.repository.dto.Pet
import com.petmily.util.CheckPermission
import com.petmily.util.GalleryUtil

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {

    private val TAG = "petmily_PetInfoFragment"
    private lateinit var mainActivity: MainActivity

    private lateinit var myPetAdapter: MyPetAdapter
    private lateinit var boardAdapter: BoardAdapter
    private lateinit var curationAdapter: CurationAdapter

    private lateinit var galleryUtil: GalleryUtil
    private lateinit var checkPermission: CheckPermission
    private val mainViewModel: MainViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val petViewModel: PetViewModel by activityViewModels()

    private val itemList = mutableListOf<Any>() // 아이템 리스트 (NormalItem과 LastItem 객체들을 추가)

    // 피드 게시물 데이터 TODO: api 통신 후 적용되는 실제 데이터로 변경
    private val boards =
        listOf(
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
            Board(),
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUserInfo()
        initAdapter()
        initPetItemList()
        initTabLayout()
        initBoards()
        initDrawerLayout()
        initImageView()
        initObserve()
    }

    private fun initUserInfo() = with(binding) {
        userViewModel.mypageInfo.value?.apply {
            // 유저 프로필 이미지
            Glide.with(mainActivity)
                .load(this?.userProfileImg)
                .circleCrop()
                .into(ivMypageUserImage)

            // 유저 닉네임
            tvUserName.text = this?.userNickname ?: ""

            // 유저 뱃지
            //            Glide.with(mainActivity)
            //                .load(this?.user)
            //                .circleCrop()
            //                .into(ivBadge)

            // 게시글, 팔로우, 팔로잉 수
            tvMypageFeedCnt.text = boardCount?.toString() ?: "0"
            tvMypageFollowCnt.text = followerCount?.toString() ?: "0"
            tvMypageFollowingCnt.text = followingCount?.toString() ?: "0"
        }
    }

    private fun initImageView() = with(binding) {
        // 설정창
        ivMypageOption.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }

        // 상점
        ivShopIcon.setOnClickListener {
            mainActivity.changeFragment("shop")
        }
    }

    private fun initDrawerLayout() = with(binding) {
        llDrawerProfile.setOnClickListener { // 프로필 수정
            userViewModel.fromUserInfoInput = "mypage"
            mainActivity.changeFragment("userInfoInput")
        }

        llDrawerPassword.setOnClickListener { // 비밀번호 변경
        }

        llDrawerPoint.setOnClickListener { // 포인트 적립 사용 내역
            // todo API 요청

            mainActivity.changeFragment("pointLog")
        }

        llDrawerSettingNotification.setOnClickListener { // 알림 설정
        }

        llDrawerSettingWithdrawal.setOnClickListener { // 회원 탈퇴
            context?.let { // context가 null이 아닐 때만 다이얼로그를 띄웁니다.
                val dialog = WithDrawalDialog(it, mainViewModel)
                dialog.show()
            }
        }

        llDrawerSettingAppInfo.setOnClickListener { // 앱 정보
        }

        llDrawerLogout.setOnClickListener { // 로그아웃
            context?.let { // context가 null이 아닐 때만 다이얼로그를 띄웁니다.
                val dialog = LogoutDialog(mainActivity, mainViewModel)
                dialog.show()
            }
        }
    }

    private fun initObserve() {
        userViewModel.mypageInfo.observe(viewLifecycleOwner) {
            initPetItemList()
        }
    }

    private fun initTabLayout() = with(binding) {
        tlMypage.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // 선택된 탭에 따라 RecyclerView의 가시성을 변경합니다.
                when (tab.position) {
                    0 -> {
                    }

                    1 -> {
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // 선택 해제된 탭에 따라 RecyclerView의 가시성을 변경합니다.
                // 예를 들어, 선택된 탭이 0번 탭이었고 1번 탭으로 변경되는 경우에는 0번 탭의 RecyclerView를 숨깁니다.
                when (tab.position) {
                    0 -> {
                    }

                    1 -> {
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                // 탭이 이미 선택된 상태에서 다시 선택되었을 때의 동작을 정의합니다.
                // 필요한 경우 이 함수를 구현하십시오.
            }
        })
    }

    private fun initPetItemList() {
        itemList.clear()
        // NormalItem 등록
        if (!userViewModel.mypageInfo.value?.userPets.isNullOrEmpty()) {
            for (petData in userViewModel.mypageInfo.value!!.userPets) {
                itemList.add(NormalItem(petData))
            }
        }

        // LastItem 등록
        itemList.add(LastItem("Last Item"))
        myPetAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() = with(binding) {
        // pet
        myPetAdapter = MyPetAdapter(itemList, ::onNormalItemClick, ::onLastItemClick)
        rcvMypageMypet.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        rcvMypageMypet.adapter = myPetAdapter

        // 게시글 adapter
        boardAdapter = BoardAdapter(mainActivity)
        rcvMypageBoard.apply {
            adapter = boardAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    // 피드 게시물 데이터 초기화 TODO: api 통신 코드로 변경
    private fun initBoards() {
        boardAdapter.setBoards(boards)
    }

    // NormalItem 클릭 이벤트 처리 (등록된 펫 정보 보기) - petViewModel
    private fun onNormalItemClick(normalItem: NormalItem) {
        Log.d(TAG, "onNormalItemClick: $normalItem")
        petViewModel.selectPetInfo = normalItem.pet
        mainActivity.changeFragment("petInfo")
    }

    // LastItem 클릭 이벤트 처리 (신규 펫 등록)
    private fun onLastItemClick(lastItem: LastItem) {
        petViewModel.fromPetInfoInputFragment = "MyPageFragment"
        mainActivity.changeFragment("petInfoInput")
    }
}
