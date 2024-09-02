package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.*;
import com.himedia.rentmon_back.util.ImageFileupload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository ur;
    private final CouponRepository cr;
    private final ReservationRepository rr;
    private final ZzimRepositroy zr;
    private final InquiryRepository ir;

    private final ImageFileupload imgf;


    public User getUserInfo(String userid) {
        return ur.findByUserid(userid);
    }

    public Map<String , Integer> getMenuCount(String userid) {
        Map<String , Integer> menus = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        menus.put("couponCount",cr.findByUseridCount(userid,now));
        menus.put("reservCount",rr.findByUseridCount(userid ,now));
        menus.put("usesapceCount",rr.findByUseridWithusedCount(userid ,now));
        menus.put("zzimCount",zr.findByUseridCount(userid));
        menus.put("inquiryCount", ir.countByUserUserid(userid));
        return menus;
    }

    public void setProfileimg(String userid, MultipartFile profileimg) {
        User user = ur.findByUserid(userid);
        if(user.getProfileimg() != null) imgf.removeFile(user.getProfileimg(), "profile_images");
        user.setProfileimg(null);
        if (profileimg != null ) {
            try {
                user.setProfileimg(imgf.saveFile(profileimg , "profile_images"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ur.save(user);

    }

    public void updatename(String userid, String name) {
        User user = ur.findByUserid(userid);
        user.setName(name);
        ur.save(user);
    }

    public void updatephone(String userid, String phone) {
        User user = ur.findByUserid(userid);
        user.setPhone(phone);
        ur.save(user);
    }


}
