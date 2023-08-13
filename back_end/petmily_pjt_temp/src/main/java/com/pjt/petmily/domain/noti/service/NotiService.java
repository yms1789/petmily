//package com.pjt.petmily.domain.noti.service;
//
//
//import com.pjt.petmily.domain.noti.entity.Noti;
//import com.pjt.petmily.domain.noti.repository.NotiRepository;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import lombok.RequiredArgsConstructor;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class NotiService {
//    private final NotiRepository notiRepository;
//
//    HttpServletRequest req;
//
//    @Autowired
//    private HttpSession session;
//    /*
//     * private HttpSession session = req.getSession();
//     */
//
//    @Transactional(readOnly = true)
//    public List<Noti> 알림리스트(int loginUserId) {
//        List<Noti> Noti = notiRepository.mNotiForHeader(loginUserId);
//        /*
//         * List<Noti> Noti = notiRepository.findByToUserId(loginUserId);
//         */
//
//        session.setAttribute("staticNoti", Noti);
//        return Noti;
//    }
//
//}