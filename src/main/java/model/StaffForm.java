package model;

import org.springframework.web.multipart.MultipartFile;

public class StaffForm {
    private Long id;
    private String name;
    private String date;
    private MultipartFile avatar;
    private boolean Gender;

    public StaffForm() {
    }

    public StaffForm(Long id, String name, String date, MultipartFile avatar, boolean gender) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.avatar = avatar;
        Gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public boolean isGender() {
        return Gender;
    }

    public void setGender(boolean gender) {
        Gender = gender;
    }
}
