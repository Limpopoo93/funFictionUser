package FunFictionUserProject.funFictionUser.dto;

import FunFictionUserProject.funFictionUser.view.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsRequestDto implements Serializable {
    private Long id;
    private String typeTags;
    private Boolean checked;

    public static TagsRequestDto fromTags(Tags tags) {
        TagsRequestDto tagsRequest = new TagsRequestDto();
        tagsRequest.setId(tags.getId());
        tagsRequest.setTypeTags(tags.getTypeTags());
        return tagsRequest;
    }
}
