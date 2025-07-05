package mayankSuperApp.auth_service.service;

import mayankSuperApp.auth_service.entity.SittingMember;

import java.util.List;

public interface SittingMemberService {
    List<SittingMember> getMembersByConstituency(String constituency);
}
