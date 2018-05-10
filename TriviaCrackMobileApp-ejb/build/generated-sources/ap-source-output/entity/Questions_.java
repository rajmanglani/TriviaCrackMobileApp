package entity;

import entity.Categories;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-15T15:55:06")
@StaticMetamodel(Questions.class)
public class Questions_ { 

    public static volatile SingularAttribute<Questions, String> text;
    public static volatile SingularAttribute<Questions, String> correctAnswer;
    public static volatile SingularAttribute<Questions, Categories> category;
    public static volatile SingularAttribute<Questions, String> qid;
    public static volatile SingularAttribute<Questions, Integer> questionLevel;
    public static volatile SingularAttribute<Questions, String> questionType;

}