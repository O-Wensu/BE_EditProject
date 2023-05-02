package edit.edit.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum JobEnum {
    YOUTUBER("youtuber"),
    EDITOR("editor");

    private String job;

    JobEnum(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    @JsonCreator(mode=JsonCreator.Mode.DELEGATING)
    public static JobEnum get(String code) {
        return Arrays.stream(JobEnum.values())
                .filter(type -> type.getJob().equals(code))
                .findAny()
                .orElse(null);
    }
}
