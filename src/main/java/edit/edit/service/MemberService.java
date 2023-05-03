package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.member.LoginRequestDto;
import edit.edit.dto.member.MemberResponseDto;
import edit.edit.dto.member.SignupRequestDto;
import edit.edit.dto.profile.ProfileRequestDto;
import edit.edit.entity.Member;
import edit.edit.entity.Profile;
import edit.edit.jwt.JwtUtil;
import edit.edit.repository.MemberRepository;
import edit.edit.repository.ProfileRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    /**
     * 회원가입
     */
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        String userId = signupRequestDto.getUserId();
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();
        String email = signupRequestDto.getEmail();

        //유효성 검사
        validExistId(userId);
        validExistNickname(nickname);
        validExistEmail(email);

        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        Profile profile = profileRequestDto.toEntity();

        // 영속성 전이(cascade) 때문에 프로필만 추가해도 자동으로 사용자가 추가됨.
        profile.addMember(signupRequestDto.toEntity(encodedPassword));
        profileRepository.save(profile);
        return ResponseDto.setSuccess("signup success", null);
    }

    /**
     * 로그인
     */
    public ResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        //유효성 검사
        Member member = validateIsMember(userId);
        validatePassword(member, password);
        response.addHeader(jwtUtil.ACCESS_HEADER, jwtUtil.createToken(userId));

        return ResponseDto.setSuccess("login success", new MemberResponseDto(member));
    }

    public ResponseDto memberInfo(HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        String userId = jwtUtil.getUserInfoFromToken(token);
        Member member = validateIsMember(userId);
        return ResponseDto.setSuccess("memberInfo success", new MemberResponseDto(member));
    }

    //====유효성 검사====

    private Member validateIsMember(String userId) {
        return memberRepository.findByUserId(userId).orElseThrow(
                () -> new IllegalArgumentException("등록된 회원이 없습니다.")
        );
    }

    private void validExistId(String userId) {
        memberRepository.findByUserId(userId)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 ID입니다.");
                });
    }
    private void validExistNickname(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
                });
    }
    private void validExistEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("해당 이메일로 가입된 회원이 존재합니다.");
                });
    }

    private void validatePassword(Member member, String password) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
