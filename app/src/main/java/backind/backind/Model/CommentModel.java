package backind.backind.Model;

/**
 * Created by root on 28/05/17.
 */

public class CommentModel {
    private int pic;
    private String name, comment;

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public CommentModel(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }
}
