package com.nexus.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class PlaylistApiExternaDto {

    @JsonProperty("items")
    private List<PlaylistItem> items;

    public List<PlaylistItem> getItems() {
        return items;
    }

    public void setItems(List<PlaylistItem> items) {
        this.items = items;
    }

    public List<String> getVideoIds() {
        return items.stream()
                .map(item -> item.getSnippet().getResourceId().getVideoId())
                .collect(Collectors.toList());
    }

    public List<String> getTitle() {
        return items.stream()
                .map(item -> item.getSnippet().getTitle())
                .collect(Collectors.toList());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PlaylistItem {

        @JsonProperty("snippet")
        private Snippet snippet;

        public Snippet getSnippet() {
            return snippet;
        }

        public void setSnippet(Snippet snippet) {
            this.snippet = snippet;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Snippet {

        @JsonProperty("resourceId")
        private ResourceId resourceId;

        @JsonProperty("title")
        private String title;

        public ResourceId getResourceId() {
            return resourceId;
        }

        public void setResourceId(ResourceId resourceId) {
            this.resourceId = resourceId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResourceId {

        @JsonProperty("videoId")
        private String videoId;

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }
}
