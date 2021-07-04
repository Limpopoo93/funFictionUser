package FunFictionUserProject.funFictionUser.util;

public interface UrlConstant {
    String USER = "/user";
    String SAVE = "/save";
    String LOGIN_URL = "/login";
    String SETTING_USER = "/userSettingPersonalData";
    String UPDATE_USER = "/updateUser";
    String LIST_FUN_FIC = "/listAllFunFiction";
    String DELETE_FUN_FIC = "/deleteFunFicByUser/{id}";
    String ADD_FUN_FIC = "/addFunFicByUser";
    String UPDATE_FUN_FIC = "/updatedFunFicByUser";
    String SEARCH_FUN_FIC = "/searchFunFiction/{name}";
    String ADMIN = "/admin";
    String UPDATE_ADMIN = "/updatedUserByAdmin/{id}";
    String LIST_USER = "/allUserByAdmin";
    String DELETE_USER = "/deleteUserByAdmin/{id}";
    String BLOCK_USER = "/blockUserByAdmin/{id}";
    String INFO_USER = "/infoUserByAdmin/{id}";
    String DELETE_FUN_FIC_ADMIN = "/deleteFunFunficByAdmin/{id}";
    String UPDATE_FUN_FIC_ADMIN = "/updatedFunficByAdmin";
}
