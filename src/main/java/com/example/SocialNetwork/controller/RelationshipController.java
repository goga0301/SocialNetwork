package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.dto.RelationshipRequest;
import com.example.SocialNetwork.dto.RelationshipResponse;
import com.example.SocialNetwork.repository.UserRepository;
import com.example.SocialNetwork.service.RelationshipService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@RequestMapping("/api/relationship")
public class RelationshipController {
    private final RelationshipService relationshipService;
    private final UserRepository userRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendFriendRequest(@RequestBody RelationshipRequest relationshipRequest){
        relationshipService.sendRequest(relationshipRequest);
        return new ResponseEntity<>("Friend Request sent!",HttpStatus.OK);
    }

    @PostMapping("/reply/{userId}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long userId){
        relationshipService.acceptRequest(userId);
        return new ResponseEntity<>(userRepository.findById(userId).get().getUsername()+" Request accepted!",HttpStatus.OK);
    }

    @PostMapping("/block/{userId}")
    public ResponseEntity<String> blockSomeone(@PathVariable Long userId){
        relationshipService.block(userId);
        return new ResponseEntity<>(userRepository.findById(userId).get().getUsername()+" Blocked!",HttpStatus.OK);
    }

    @PostMapping("/delete/{userId}")
    public ResponseEntity<String> deleteRelationship(@PathVariable Long userId){
        System.out.println("i am here");
        relationshipService.delete(userId);
        return new ResponseEntity<>(userRepository.findById(userId).get().getUsername()+" Deleted!",HttpStatus.OK);

    }

    @GetMapping("/friendrequests")
    public ResponseEntity<List<RelationshipResponse>> getAllFriendRequest(){
        return status(HttpStatus.OK).body(relationshipService.getAllFriendRequest());
    }
    @GetMapping("/allFriend")
    public ResponseEntity<List<RelationshipResponse>> getAllFriend(){
        return status(HttpStatus.OK).body(relationshipService.getAllFriend());
    }
    @GetMapping("/myActionsByStatus/{status}")
    public ResponseEntity<List<RelationshipResponse>> getMyActions(@PathVariable Integer status){
        return status(HttpStatus.OK).body(relationshipService.getMyActions(status));
    }


    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<RelationshipResponse>> getAllByStatus(@PathVariable Integer status){
        return status(HttpStatus.OK).body(relationshipService.getAllRelationshipsByStatus(status));
    }
}
