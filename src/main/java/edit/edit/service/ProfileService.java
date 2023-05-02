package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.profile.ProfileRequestDto;
import edit.edit.dto.profile.ProfileResponseDto;
import edit.edit.entity.Member;
import edit.edit.entity.Profile;
import edit.edit.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public ResponseDto getProfile(Long memberId) {
        Profile profile = existProfile(memberId);
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(profile);
        return ResponseDto.setSuccess("getProfile success", profileResponseDto);
    }

    @Transactional
    public ResponseDto update(Long id, ProfileRequestDto profileRequestDto, Member member) {
        Profile profile = existProfile(id);
        isProfileMember(member, profile);
        profile.updateProfile(profileRequestDto);

        return ResponseDto.setSuccess("profile update success", profile);
    }

    private Profile existProfile(Long memberId){
        return profileRepository.findByMemberId(memberId).orElseThrow(
                () -> new NoSuchElementException("프로필을 작성한 회원이 없습니다.")
        );
    }

    private void isProfileMember(Member member, Profile profile){
        if(!profile.getMember().getUserId().equals(member.getUserId())){
            throw new IllegalArgumentException("일치하는 회원이 아닙니다.");
        }
    }

}
