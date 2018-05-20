package backind.backind.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileResponse    {
        @SerializedName("id_user")
        @Expose
        private Integer idUser;
        @SerializedName("id_roles")
        @Expose
        private String idRoles;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getIdUser() {
            return idUser;
        }

        public void setIdUser(Integer idUser) {
            this.idUser = idUser;
        }

        public String getIdRoles() {
            return idRoles;
        }

        public void setIdRoles(String idRoles) {
            this.idRoles = idRoles;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
