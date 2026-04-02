package org.example.mediqback.addfamily.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FamilyDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Req {
        private String name;
        private int age;
        private String bloodtype;
        private String relation;
        private Long userIdx;



        public Family toEntity(Req dto) {
            return Family.builder()
                    .name(dto.getName())
                    .age(dto.getAge())
                    .bloodtype(dto.getBloodtype())
                    .relation(dto.getRelation())
                    .userIdx(dto.getUserIdx())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class Res {
        private Long idx;
        private String name;
        private int age;
        private String bloodtype;
        private String relation;

        public static Res from(Family entity) {
            return Res.builder()
                    .idx(entity.getIdx())
                    .name(entity.getName())
                    .age(entity.getAge())
                    .bloodtype(entity.getBloodtype())
                    .relation(entity.getRelation())
                    .build();
        }
    }

//    @Builder
//    @Getter
//    @AllArgsConstructor
//    public static class ListRes {
//        private Long idx;
//        private
//    }
}
