package com.petmily.presentation.view

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseActivity
import com.petmily.databinding.ActivityMainBinding
import com.petmily.presentation.view.board.BoardDetailFragment
import com.petmily.presentation.view.board.BoardWriteFragment
import com.petmily.presentation.view.certification.join.JoinFragment
import com.petmily.presentation.view.certification.login.LoginFragment
import com.petmily.presentation.view.certification.password.PasswordFragment
import com.petmily.presentation.view.chat.ChatDetailFragment
import com.petmily.presentation.view.chat.ChatUserListFragment
import com.petmily.presentation.view.curation.CurationDetailFragment
import com.petmily.presentation.view.curation.CurationMainFragment
import com.petmily.presentation.view.curation.WebViewFragment
import com.petmily.presentation.view.gallery.GalleryFragment
import com.petmily.presentation.view.home.HomeFragment
import com.petmily.presentation.view.info.pet.PetInfoFragment
import com.petmily.presentation.view.info.pet.PetInfoInputFragment
import com.petmily.presentation.view.info.user.UserInfoInputFragment
import com.petmily.presentation.view.mypage.MyPageFragment
import com.petmily.presentation.view.notification.NotificationFragment
import com.petmily.presentation.view.search.SearchFragment
import com.petmily.presentation.view.store.PointLogFragment
import com.petmily.presentation.view.store.ShopFragment
import com.petmily.presentation.viewmodel.BoardViewModel
import com.petmily.presentation.viewmodel.CurationViewModel
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Board

private const val TAG = "petmily_MainActivity"
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    lateinit var bottomNavigationView: BottomNavigationView
    private val mainViewModel: MainViewModel by viewModels()
    private val curationViewModel: CurationViewModel by viewModels()
    private val boardViewModel: BoardViewModel by viewModels()

    private lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()

        bottomNavigationView = binding.bottomNavigation

        Log.d(TAG, "onCreate: ${ApplicationClass.sharedPreferences.getString("userEmail")} / ${ApplicationClass.sharedPreferences.getString("userNickname")}")
        ApplicationClass.sharedPreferences.apply {
            if (!getString("userEmail").isNullOrBlank() && !getString("userNickname").isNullOrBlank()) {
                curationViewModel.requestCurationData("all", mainViewModel)
            } else if (!getString("userEmail").isNullOrBlank() && getString("userNickname").isNullOrBlank()) {
                changeFragment("userInfoInput")
            } else {
                changeFragment("login")
            }
        }

        initBottomNavigation()
        initFragmentAnimation()

        // todo 임시 호출 (호출 로직 다시 생각해야함 -> 언제 데이터를 받아올지?)
//        curationViewModel.requestCurationData("all", mainViewModel)
//
//        supportFragmentManager.commit {
//            replace(R.id.frame_layout_main, LoginFragment())
//        }
//        bottomNavigationView.visibility = View.VISIBLE
    }
    
    private fun initObserver() {
        // Connect Exception
        mainViewModel.connectException.observe(this) {
            Log.d(TAG, "ConnectException: 서버 연결 오류")
            showSnackbar("서버 연결에 실패하였습니다.")
        }
        
        // 큐레이션 초기 데이터 GET
        curationViewModel.curationAllList.observe(this@MainActivity) {
            Log.d(TAG, "initObserver: curationAllList $it")
            Log.d(TAG, "initObserver: curationAllList ${curationViewModel.curationDogList.value}")
            curationViewModel.getRandomCurationList()
        }
        
        curationViewModel.randomCurationList.observe(this@MainActivity) {
            Log.d(TAG, "initObserver: randomCurationList $it")
            changeFragment("home")
            bottomNavigationView.visibility = View.VISIBLE
        }
    }
    
    /**
     * Fragment 애니메이션 전환
     */
    fun initFragmentAnimation(): FragmentTransaction {
        return supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.fragment_to_right,
            R.anim.fragment_from_right,
            R.anim.fragment_to_left,
            R.anim.fragment_from_left,
        )
    }

    fun initBottomNavigation() = with(binding) {
        bottomNavigationView.apply {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_page_home -> {
                        changeFragment("home")
                        true
                    }

                    R.id.navigation_page_curation -> {
                        changeFragment("curation")
                        true
                    }

                    R.id.navigation_page_feed_add -> {
                        // 수정 및 삭제용 Board 초기화
                        boardViewModel.selectedBoard = Board()
                        
                        changeFragment("feed add")
                        true
                    }

                    R.id.navigation_page_chatting -> {
                        changeFragment("chatting")
                        true
                    }

                    R.id.navigation_page_my_page -> {
                        changeFragment("my page")
                        true
                    }

                    else -> false
                }

                true
            }
        }
    }

    fun bottomNaviVisible() {
        bottomNavigationView.visibility = View.VISIBLE
    }

    fun changeFragment(str: String) {
        when (str) {
            "login" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, LoginFragment())
                }
            }

            "password" -> {
                supportFragmentManager.commit {
                    addToBackStack("login")
                    replace(R.id.frame_layout_main, PasswordFragment())
                }
            }

            "join" -> {
                supportFragmentManager.commit {
                    addToBackStack("login")
                    replace(R.id.frame_layout_main, JoinFragment())
                }
            }

            "home" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, HomeFragment())
                }
            }

            "curation" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, CurationMainFragment())
                }
            }

            "feed add" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, BoardWriteFragment())
                }
            }

            "chatting" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, ChatUserListFragment())
                }
            }

            "my page" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, MyPageFragment())
                }
            }

            "userInfoInput" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, UserInfoInputFragment())
                }
            }

            "petInfoInput" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, PetInfoInputFragment())
                }
            }

            "curation detail" -> {
                initFragmentAnimation().apply {
                    addToBackStack("curation detail")
                    replace(R.id.frame_layout_main, CurationDetailFragment())
                }.commit()
            }

            "gallery" -> {
                supportFragmentManager.commit {
                    addToBackStack("gallery")
                    replace(R.id.frame_layout_main, GalleryFragment())
                }
            }

            "search" -> {
                supportFragmentManager.commit {
                    addToBackStack("search")
                    replace(R.id.frame_layout_main, SearchFragment())
                }
            }

            "chat detail" -> {
                supportFragmentManager.commit {
                    addToBackStack("chat detail")
                    replace(R.id.frame_layout_main, ChatDetailFragment())
                }
            }

            "notification" -> {
                supportFragmentManager.commit {
                    addToBackStack("notification")
                    replace(R.id.frame_layout_main, NotificationFragment())
                }
            }

            "petInfo" -> {
                supportFragmentManager.commit {
                    replace(R.id.frame_layout_main, PetInfoFragment())
                }
            }

            "board detail" -> {
                supportFragmentManager.commit {
                    addToBackStack("board detail")
                    replace(R.id.frame_layout_main, BoardDetailFragment())
                }
            }

            "webView" -> {
                supportFragmentManager.commit {
                    addToBackStack("webView")
                    replace(R.id.frame_layout_main, WebViewFragment())
                }
            }
            
            "pointLog" -> {
                supportFragmentManager.commit {
                    addToBackStack("pointLog")
                    replace(R.id.frame_layout_main, PointLogFragment())
                }
            }
            
            "shop" -> {
                supportFragmentManager.commit {
                    addToBackStack("shop")
                    replace(R.id.frame_layout_main, ShopFragment())
                }
            }
        }
    }
}
