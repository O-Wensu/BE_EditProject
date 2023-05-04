package edit.edit.service;

import edit.edit.dto.ResponseDto;
import edit.edit.dto.chat.ChatDto;
import edit.edit.dto.chat.ChatRoomRequestDto;
import edit.edit.dto.chat.ChatRoomResponseDto;
import edit.edit.entity.ChatRoom;
import edit.edit.entity.JoinChatRoom;
import edit.edit.entity.Member;
import edit.edit.repository.ChatRoomRepository;
import edit.edit.repository.JoinChatRoomRepository;
import edit.edit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final JoinChatRoomRepository joinChatRoomRepository;

    public ResponseDto createChatRoom(ChatRoomRequestDto requestDto, Member sender) {
            Member receiver = memberRepository.findByNickname(requestDto.getReceiver()).get();
            //이미 reciever와 sender로 생성된 채팅방이 있는지 확인
            JoinChatRoom findChatRoom = findExistChatRoom(receiver, sender);

            //채팅방 있으면 ChatRoom의 roomId 반환
            if (findChatRoom != null){
                return ResponseDto.setSuccess("already has room and find Chat Room Success!", new ChatRoomResponseDto(findChatRoom, "EXIST"));
            }

            //채팅방 없으면 receiver와 sender의 방을 생성해주고 roomId 반환
            ChatRoom newChatRoom = new ChatRoom();
            JoinChatRoom newJoinChatRoom = new JoinChatRoom(sender, newChatRoom, receiver.getNickname());
            newChatRoom.addJoinChatRoom(newJoinChatRoom);
            newChatRoom.addJoinChatRoom(new JoinChatRoom(receiver, newChatRoom, sender.getNickname()));
            chatRoomRepository.save(newChatRoom);
            return ResponseDto.setSuccess("create ChatRoom success", new ChatRoomResponseDto(newJoinChatRoom, "NEW"));
    }

    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        // 채팅방 찾기
        ChatRoom chatRoom = findExistChatRoom(chatDto.getRoomId());
        // 예외처리
        //반환 결과를 socket session에 사용자의 id로 저장
        headerAccessor.getSessionAttributes().put("nickname", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("roomId", chatDto.getRoomId());

        //chatDto.setMessage(chatDto.getSender() + "님 입장!! ο(=•ω＜=)ρ⌒☆");
        Long sender_id = memberRepository.findByNickname(chatDto.getSender()).get().getId();
        JoinChatRoom joinChatRoom = joinChatRoomRepository.findByChatRoomIdAndMemberId(chatRoom.getId(), sender_id).get();
        chatDto.setRoomName(joinChatRoom.getRoomName());
        return chatDto;
    }

    public ResponseDto findChatRoom(ChatRoomRequestDto receiverNickname, Member sender){
        Member receiver = memberRepository.findByNickname(receiverNickname.getReceiver()).get();
        JoinChatRoom findChatRoom = findExistChatRoom(receiver, sender);
        return ResponseDto.setSuccess("find your chatting room", new ChatRoomResponseDto(findChatRoom, "EXIST"));
    }

    public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickname");

        Long chatRoomId = chatRoomRepository.findByRoomId(roomId).get().getId();
        Long memberId = memberRepository.findByNickname(nickName).get().getId();
        String roomName = joinChatRoomRepository.findByChatRoomIdAndMemberId(chatRoomId, memberId).get().getRoomName();

        ChatDto chatDto = ChatDto.builder()
                .type(ChatDto.MessageType.LEAVE)
                .roomId(roomId)
                .sender(nickName)
                .roomName(roomName)
                //.message(nickName + "님 퇴장!! ヽ(*。>Д<)o゜")
                .build();

        return chatDto;
    }

    private JoinChatRoom findExistChatRoom(Member receiver, Member sender) {
        // JoinChatRoom 엔티티를 사용하여 sender와 receiver가 각각 속한 채팅방 정보를 조회
        Optional<List<JoinChatRoom>> joinChatRoomReceiver = joinChatRoomRepository.findAllByMemberId(receiver.getId());

        //속해있는 채팅방이 없으면
        if (!joinChatRoomReceiver.isPresent()) {
            log.info("joinChatRoom findByMemberId 결과가 null!");
            return null;
        }

        List<JoinChatRoom> receiverJoinChatRooms = joinChatRoomReceiver.get();

        JoinChatRoom joinChatRoom = null;
        for(JoinChatRoom receiverRooms : receiverJoinChatRooms){
            Long receiverRoomId = receiverRooms.getChatRoom().getId();
            Optional<JoinChatRoom> targetJCR = joinChatRoomRepository.findByChatRoomIdAndMemberId(receiverRoomId, sender.getId());

            if(targetJCR.isPresent()){
                joinChatRoom = targetJCR.get();
            }
        }
        return joinChatRoom;
    }

    private ChatRoom findExistChatRoom(String roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElseThrow(
                () -> new NoSuchElementException("채팅방이 존재하지 않습니다.")
        );
    }
}
