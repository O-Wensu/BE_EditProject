package edit.edit.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomIdResponseDto {
    private String roomId;
    private String type;
    private String sender;

    public ChatRoomIdResponseDto(String roomId, String sender) {
        this.roomId = roomId;
        this.type = "SUB";
        this.sender = sender;
    }
}
