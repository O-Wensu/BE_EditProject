package edit.edit.dto.profile;

import edit.edit.dto.member.MemberResponseDto;
import edit.edit.entity.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {
    private Long id;
    private String editTool;
    private String career;
    private String form;
    private int salary;
    private String genre;
    private String workStyle;
    private String specialty;
    private String video;
    private String homepage;
    private String img;
    private MemberResponseDto member;

    public ProfileResponseDto(Profile profile){
        this.id=profile.getId();
        this.editTool=profile.getEditTool();
        this.career=profile.getCareer();
        this.form=profile.getForm();
        this.salary=profile.getSalary();
        this.genre=profile.getGenre();
        this.workStyle= profile.getWorkStyle();
        this.specialty=profile.getSpecialty();
        this.video=profile.getVideo();
        this.homepage=profile.getHomepage();
        this.img=profile.getImg();
        this.member=new MemberResponseDto(profile.getMember());
    }
}
