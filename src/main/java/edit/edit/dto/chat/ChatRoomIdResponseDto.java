package edit.edit.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomIdResponseDto {
    private String roomId;
    private String type;
    private String sender;

    public ChatRoomIdResponseDto(ChatRoomIdRequestDto chatRoomIdRequestDto) {
        this.roomId = chatRoomIdRequestDto.getRoomId();
        this.type = "SUB";
        this.sender = chatRoomIdRequestDto.getSender();
    }
}
