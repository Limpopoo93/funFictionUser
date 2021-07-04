package FunFictionUserProject.funFictionUser.dto;

import FunFictionUserProject.funFictionUser.view.FunFiction;
import FunFictionUserProject.funFictionUser.view.Genre;
import FunFictionUserProject.funFictionUser.view.Tags;
import FunFictionUserProject.funFictionUser.view.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FunFicRequestDto implements Serializable {
    private Long id;
    private String nameFun;
    private String shortDescription;
    private Double rating;
    private Integer like;
    private Date created;
   // private Genre genre;
    private String genre;
    private Long idUser;
   // private List<String> tags;
   // private List<Tags> tags;
/*
    public FunFiction toFunFic() {
        FunFiction funFiction = new FunFiction();
        funFiction.setId(id);
        funFiction.setNameFun(nameFun);
        funFiction.setShortDescription(shortDescription);
        funFiction.setCreated(created);
        funFiction.setLike(like);
        funFiction.setRating(rating);
        funFiction.setTags(tags);
        funFiction.setGenre(genre);
        funFiction.setUser(user);
        return funFiction;
    }


 */
    public static FunFicRequestDto fromFunFic(FunFiction funFiction) {
        FunFicRequestDto funFicRequestDto = new FunFicRequestDto();
        funFicRequestDto.setId(funFiction.getId());
        funFicRequestDto.setNameFun(funFiction.getNameFun());
        funFicRequestDto.setShortDescription(funFiction.getShortDescription());
        funFicRequestDto.setCreated(funFiction.getCreated());
        funFicRequestDto.setLike(funFiction.getLike());
        funFicRequestDto.setRating(funFiction.getRating());
        //funFicRequestDto.setTags(funFiction.getTags());
        funFicRequestDto.setGenre(funFiction.getGenre().getTypeGenre());
        return funFicRequestDto;
    }
}
