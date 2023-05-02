package recenze;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Entity
@Named
@RequestScoped
public class RecenzeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recenzeId;
    @Column
    private String content;



    public long getRecenzeId() {
        return recenzeId;
    }

    public void setRecenzeId(long recenzeId) {
        this.recenzeId = recenzeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
