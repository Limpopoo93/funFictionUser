package FunFictionUserProject.funFictionUser.dto;

import FunFictionUserProject.funFictionUser.view.Favorites;
import FunFictionUserProject.funFictionUser.view.FunFiction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequestDto implements Serializable {
    private Long id;
    private Long idUser;
    private String status;
    private Long idFunFic;
    private Long idChapter;

    public static FavoriteRequestDto fromFavorites(Favorites favorites) {
        FavoriteRequestDto favoriteRequestDto = new FavoriteRequestDto();
        favoriteRequestDto.setId(favorites.getId());
        favoriteRequestDto.setStatus(String.valueOf(favorites.getStatus()));
        favoriteRequestDto.setIdUser(favorites.getUser().getId());
        favoriteRequestDto.setIdFunFic(favorites.getFunFiction().getId());

        return favoriteRequestDto;
    }
}
