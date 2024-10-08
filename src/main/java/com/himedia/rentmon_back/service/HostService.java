package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.repository.HostRepository;
import com.himedia.rentmon_back.repository.MemberRepository;
import com.himedia.rentmon_back.repository.SpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HostService {

    private final HostRepository hr;
    private final MemberRepository memberRepository;

//    public Host getHost(String email) {
//        // Optional : 검색결과가  null 이어서 발생할 수 있는 예외처리나 에러를 방지하지 하기위한 자바의 도구입니다.  null  값이 있을지도 모를 객체를 감싸서  null 인데 접근하려는 것을 사전에 차단합니다.  다음과 같이 검증을 거친후 사용되어집니다
//        Optional<Host> host = hr.findByEmail( email );
//        //  isPresent() : 해당 객체가 인스턴스를 저장하고 있다면 true , null 이면  flase 를 리턴
//        // isEmpty() : isPresent()의 반대값을 리턴합니다
//        if( !host.isPresent() ){
//            return null;
//        }else {
//            // get() : Optional 내부 객체를 꺼내서 리턴합니다
//            return host.get();
//        }
//    }

//    public Host getHostBySnsid(String id) {
//        Optional<Host> host = hr.findBySnsid( id );
//        if( !host.isPresent() ){
//            return null;
//        }else{
//            return host.get();
//        }
//    }

    public void insertHost(Host host) {
        hr.save(host);
    }

//    public Host getHostByNickname(String nickname) {
//        Optional<Host> host = hr.findByname( nickname );
//        if( !host.isPresent() ){
//            return null;
//        }else{
//            return host.get();
//        }
//    }

    public Host getHostLogin(String userid) {
        Optional<Host> host = hr.findById(userid);
        if( !host.isPresent() ){
            return null;
        }else{
            return host.get();
        }
    }

    public void insertMember(Host host) {
//        Host mseq = hr.findOne(host.getHostid())
        hr.save(host);
    }

    public Host getHostInfo(String hostid) {
        return hr.findByHostid(hostid);
    }

    public Host findByHostid(String hostid) {
        return hr.findByHostid(hostid);
    }

    public void update(Host host) {
        hr.save(host);
//        Optional<Host> h = hr.findByNickname(host.getNickname());
//        if( h.isPresent() ){
//            Host ho = h.get();
//            ho.setEmail( host.getEmail() );
//            ho.setNickname( host.getNickname() );
//            ho.setPhone( host.getPhone() );
//            ho.setPwd( host.getPwd() );
//        }else{
//            return;
//        }
    }

    public Host getHost(String hostid) {
        return hr.findById(hostid).orElseThrow();
    }

    public void deleteHost(String hostid) {
        // 호스트의 멤버를 먼저 삭제
        Member member = memberRepository.findByUserid(hostid);
        if (member != null) {
            memberRepository.delete(member);
        }else {
            throw new RuntimeException("멤버를 찾을 수 없습니다: " + hostid);
        }

        // 호스트 삭제
        Host host = hr.findById(hostid)
                .orElseThrow(() -> new RuntimeException("호스트를 찾을 수 없습니다."));
        hr.delete(host);
    }
//
//    public Host getHost(String hostid) {return hr.getHost(hostid);}
//
//    public void insertHost(Host host) {hr.insertHost(host);}
//
//    public void withDrawal(String hostid) {
//        hr.widtDrawal(hostid);
//    }
}
