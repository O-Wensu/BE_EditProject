package edit.edit.dto.chat;

import lombok.Getter;

@Getter
public class ChatRoomIdRequestDto {
    private String sender;
    private String receiver;
    private String roomId;
}
