package com.avocado.normal.board.controller;

import com.avocado.Item.domain.entity.Category;
import com.avocado.normal.auction.controller.dto.NormalBidResponseDto;
import com.avocado.normal.auction.service.NormalAuctionService;
import com.avocado.normal.board.controller.dto.NormalItemDetailResponseDto;
import com.avocado.normal.board.controller.dto.NormalItemInfoDto;
import com.avocado.normal.board.controller.dto.NormalResponseDto;
import com.avocado.normal.board.service.NormalBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/normal")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NormalBoardController {
    private final NormalBoardService normalBoardService;
    private final NormalAuctionService normalAuctionService;


    // 예외 던지는데 exception 클래스는 만들지 않겠음
    @GetMapping("/list")
    public ResponseEntity<?> itemList() {
        NormalResponseDto itemlist = normalBoardService.getList();
        return ResponseEntity.ok().body(itemlist);
    }

    @GetMapping("/list/search/{keyword}")
    public ResponseEntity<?> searchItemList(@PathVariable String keyword) {
        NormalResponseDto itemlist = normalBoardService.getSearchList(keyword);
        return ResponseEntity.ok().body(itemlist);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> itemDetail(@PathVariable Long id){
        NormalItemDetailResponseDto itemInfo = normalBoardService.getItemRealDetail(id);
        return ResponseEntity.ok().body(itemInfo);
    }

    @GetMapping("/list/sort-category")
    public ResponseEntity<?> SortCategoryList(@RequestParam Category category) {
        NormalResponseDto itemlist = normalBoardService.getCategoryList(category);
        return ResponseEntity.ok().body(itemlist);
    }

}
