package com.nexus.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VideoApiExternaDto {

    @JsonProperty("items")
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getViews(){
        return Integer.parseInt(items.get(0).getStatistics().getViewCount());
    }

    public static class Item {

        @JsonProperty("statistics")
        private Statistics statistics;



        public Statistics getStatistics() {
            return statistics;
        }

        public void setStatistics(Statistics statistics) {
            this.statistics = statistics;
        }
    }

    public static class Statistics {

        @JsonProperty("viewCount")
        private String viewCount;

        @JsonProperty("likeCount")
        private String likeCount;

        @JsonProperty("commentCount")
        private String commentCount;

        public Statistics(String viewCount, String likeCount, String commentCount) {
            this.viewCount = viewCount;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(String likeCount) {
            this.likeCount = likeCount;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(String commentCount) {
            this.commentCount = commentCount;
        }
    }
}
