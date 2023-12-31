package com.avocado.Item.controller;

import com.avocado.Item.controller.dto.*;
import com.avocado.Item.domain.entity.Type;
import com.avocado.Item.service.ItemService;
import com.avocado.member.domain.entity.Member;
import com.avocado.member.service.AuthService;
import com.avocado.member.service.MemberService;
import com.avocado.test.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final MemberService memberService;
    private final AuthService authService;
    private final TestService testService;

    // 위탁 요청 물품 등록
    @PostMapping(path = "/consign", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> save(
            @RequestHeader("Authorization") String requestAccessToken,
            @RequestPart(value = "dto") ConsignRequestDto consignRequestDto,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        String email = authService.getPrincipal(authService.resolveToken(requestAccessToken));
        Member member = memberService.getMember(email);
        String url = testService.create(consignRequestDto.getName(), file);
        consignRequestDto.setThumbnail(url);
        if (itemService.saveItem(consignRequestDto, member)) {
            return ResponseEntity.ok().body(url);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // 마이페이지 - 나의 위탁 물품 리스트 가져오기
    @GetMapping("/my-sale")
    public ResponseEntity<?> mySales(@RequestHeader("Authorization") String requestAccessToken) {
        String email = authService.getPrincipal(authService.resolveToken(requestAccessToken));
        Long memberId = memberService.getMember(email).getId();
        return ResponseEntity.ok().body(itemService.getMySales(memberId));
    }



    // 마이페이지 - 나의 위탁 물품 상세보기
    // TODO : 예외 처리에 대한 고민해보기, 어느 단에서 분기를 해야할지 고민해보기
    @GetMapping("/my-sale/{itemId}/{type}")
    public MySaleDetailResponseDto mySaleDetail(@PathVariable(name = "itemId") Long itemId, @PathVariable(name = "type")String type) {
        if(type.equals(Type.NORMAL.getKey())) {
            return itemService.getMyNormalSale(itemId);
        } else if (type.equals(Type.LIVE.getKey())) {
            //return itemService.getMyLiveSale(itemId);
        }

        return null;
    }
    
    // 마이페이지 - 나의 입찰 상품 리스트 가져오기 (상시만)
    @GetMapping("/my-bid")
    public MyBidResponseDto myBids(@RequestHeader("Authorization") String requestAccessToken) {
        String email = authService.getPrincipal(authService.resolveToken(requestAccessToken));
        Long memberId = memberService.getMember(email).getId();

        return itemService.getMyNormalBids(memberId);
    }

    //마이페이지 - 나의 라이브 입찰 상품 리스트 가져오기
    @GetMapping("/my-livebid")
    public MyBidResponseDto myLiveBids(@RequestHeader("Authorization") String requestAccessToken) {
        String email = authService.getPrincipal(authService.resolveToken(requestAccessToken));
        Long memberId = memberService.getMember(email).getId();

        return itemService.getMyLiveBids(memberId);
    }
    
    // 마이페이지 - 나의 낙찰 상품 리스트 가져오기
    @GetMapping("/my-success-bid")
    public MySuccessBidResponseDto mySuccessBid(@RequestHeader("Authorization") String requestAccessToken) {
        String email = authService.getPrincipal(authService.resolveToken(requestAccessToken));
        Long memberId = memberService.getMember(email).getId();

        return itemService.getMySuccessBids(memberId);
        //return null;
    }

    @GetMapping("/my-success-livebid")
    public MySuccessBidResponseDto mySuccessLiveBid(@RequestHeader("Authorization") String requestAccessToken) {
        String email = authService.getPrincipal(authService.resolveToken(requestAccessToken));
        Long memberId = memberService.getMember(email).getId();

        return itemService.getMyLiveSuccessBids(memberId);
    }

}
