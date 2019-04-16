package scut.cwh.reid.domain.response;

import com.google.gson.Gson;

import java.util.List;

public class DetectResp {
    private String annotate;
    private List<String> persons;

    public String getAnnotate() {
        return annotate;
    }

    public void setAnnotate(String annotate) {
        this.annotate = annotate;
    }

    public List<String> getPersons() {
        return persons;
    }

    public void setPersons(List<String> persons) {
        this.persons = persons;
    }

    public static void main(String[] args) {
        String resp = "{'annotate': 'a.jpg', 'persons':['a1.jpg','a2.jpg']}";
        Gson gson = new Gson();
        DetectResp detectResp = gson.fromJson(resp, DetectResp.class);
        System.out.println(detectResp.persons.get(0));
    }
}
