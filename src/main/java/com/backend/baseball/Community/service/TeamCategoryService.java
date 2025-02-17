package com.backend.baseball.Community.service;

import com.backend.baseball.Community.entity.TeamCategory;
import com.backend.baseball.Community.repository.TeamCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamCategoryService {
    private final TeamCategoryRepository teamCategoryRepository;

    public TeamCategory findByTeamCategoryTitle(String teamCategoryTitle){
        Optional<TeamCategory> teamCategory=teamCategoryRepository.findByTeamCategoryTitle(teamCategoryTitle);

        if(teamCategory.isPresent()){
            return teamCategory.get();
        }

        TeamCategory newTeamCategory=new TeamCategory(teamCategoryTitle);
        TeamCategory savedTeamCategory=teamCategoryRepository.save(newTeamCategory);

        return savedTeamCategory;

    }





}
