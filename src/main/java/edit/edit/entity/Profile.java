package edit.edit.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edit.edit.dto.profile.ProfileRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Entity
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;
    @Column(nullable = false)
    @ColumnDefault("'편집툴추가'")
    private String editTool;
    @Column(nullable = false)
    @ColumnDefault("'경력없음'")
    private String career;
    @Column(nullable = false)
    @ColumnDefault("'협의가능'")
    private String form;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int salary;
    @Column(nullable = false)
    @ColumnDefault("'무관'")
    private String genre;
    @Column(nullable = false)
    @ColumnDefault("'무관'")
    private String workStyle;
    @Column(nullable = false)
    @ColumnDefault("'주특기추가'")
    private String specialty;
    @Column
    private String video;
    @Column
    private String homepage;
    @Column
    private String img;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Profile(String editTool, String career, String form, int salary, String genre, String workStyle, String specialty, String video, String homepage, String img) {
        this.editTool = editTool;
        this.career = career;
        this.form = form;
        this.salary = salary;
        this.genre = genre;
        this.workStyle = workStyle;
        this.specialty = specialty;
        this.video = video;
        this.homepage = homepage;
        this.img = img;
    }

    public void updateProfile(ProfileRequestDto profileRequestDto){
        this.editTool = profileRequestDto.getEditTool();
        this.career = profileRequestDto.getCareer();
        this.form = profileRequestDto.getForm();
        this.salary = profileRequestDto.getSalary();
        this.genre = profileRequestDto.getGenre();
        this.workStyle = profileRequestDto.getWorkStyle();
        this.specialty = profileRequestDto.getSpecialty();
        this.video = profileRequestDto.getVideo();
        this.homepage = profileRequestDto.getHomepage();
        this.img = profileRequestDto.getImg();
    }

    public void addMember(Member member){
        this.member = member;
    }
}

