package com.example.demo.member.mapper;

import com.example.demo.member.dto.MemberDto;
import com.example.demo.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    /**
     * MemberPost를 Member로 반환
     */
    Member memberPostToMember(MemberDto.Post requestBody);

    /**
     * MemberPatch를 Member로 반환
     */
    Member memberPatchToMember(MemberDto.Patch requestBody);

    /**
     * Member를 MemberDto의 Response로 반환
     */
    MemberDto.Response memberToMemberResponse(Member member);

    /**
     * List<Member>를 List<MemberDto.Response> 로 반환
     */
    List<MemberDto.Response> membersToMemberResponses(List<Member> members);
}
