package entity;

import entity.Questions;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-15T15:55:06")
@StaticMetamodel(Answeroptions.class)
public class Answeroptions_ { 

    public static volatile SingularAttribute<Answeroptions, String> optionid;
    public static volatile SingularAttribute<Answeroptions, String> optionText;
    public static volatile SingularAttribute<Answeroptions, Questions> qid;

}