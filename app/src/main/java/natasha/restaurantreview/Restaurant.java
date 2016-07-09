package natasha.restaurantreview;


import java.io.Serializable;

public class Restaurant implements Serializable {

        private long id;
        private String name;
        private String address;
        private String phone;
        private String description;
        private String tags;
        private String rating;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }



        // Will be used by the ArrayAdapter in the ListView
        @Override
        public String toString() {
            return name;
        }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
