package mayankSuperApp.auth_service.service.impl;

import mayankSuperApp.auth_service.entity.SittingMember;
import mayankSuperApp.auth_service.repository.SittingMemberRepository;
import mayankSuperApp.auth_service.service.SittingMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SittingMemberServiceImpl implements SittingMemberService {

    private final SittingMemberRepository repository;

    public SittingMemberServiceImpl(SittingMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SittingMember> getMembersByConstituency(String constituency) {
        return repository.findByConstituency(constituency);
    }
}
