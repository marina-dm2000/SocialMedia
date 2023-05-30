package com.example.socialMedia.controller;

import com.example.socialMedia.dto.community.CommunityRequestDTO;
import com.example.socialMedia.dto.community.CommunityResponseDTO;
import com.example.socialMedia.dto.community.MemberRequestDTO;
import com.example.socialMedia.model.User;
import com.example.socialMedia.repository.UserRepository;
import com.example.socialMedia.service.CommunityService;
import com.example.socialMedia.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommunityController {

    private final CommunityService communityService;
    private final UserService userService;
    private final UserRepository userRepository;


    public CommunityController(CommunityService communityService, UserService userService, UserRepository userRepository) {
        this.communityService = communityService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/communities")
    public ResponseEntity<CommunityResponseDTO> createCommunity(@Valid @RequestBody CommunityRequestDTO communityRequestDTO) {
        CommunityResponseDTO createdCommunity = communityService.createCommunity(communityRequestDTO);
        return ResponseEntity.ok().body(createdCommunity);
    }

    @GetMapping("/communities/{communityId}")
    public ResponseEntity<CommunityResponseDTO> getCommunityById(@PathVariable Long communityId) {
        return communityService.findById(communityId)
                .map(community -> ResponseEntity.ok(communityService.toCommunityResponseDTO(community)))
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/communities/{communityId}")
    public ResponseEntity<CommunityResponseDTO> updateCommunity(
            @PathVariable Long communityId,
            @Valid @RequestBody CommunityRequestDTO communityRequestDTO) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User currentUser = userService.findUserByUserName(username);

        CommunityResponseDTO updatedCommunity = communityService.updateCommunity(communityId, communityRequestDTO, currentUser);
        return ResponseEntity.ok(updatedCommunity);
    }

    @DeleteMapping("/communities/{communityId}")
    public ResponseEntity<?> deleteCommunity(@PathVariable Long communityId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User currentUser = userService.findUserByUserName(username);

        communityService.deleteCommunity(communityId, currentUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/communities/{communityId}/members")
    public ResponseEntity<?> addMember(@PathVariable Long communityId, @Valid @RequestBody MemberRequestDTO memberRequestDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User currentUser = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        User userToAdd = userRepository.findById(memberRequestDTO.getUserId()).get();

        communityService.addMember(communityId, userToAdd, currentUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/communities/{communityId}/members/{memberId}")
    public ResponseEntity<?> removeMember(@PathVariable Long communityId, @PathVariable Long memberId) {
        communityService.removeMember(communityId, memberId);
        return ResponseEntity.ok().build();
    }

}
