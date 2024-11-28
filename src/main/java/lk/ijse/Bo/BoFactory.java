package lk.ijse.Bo;

import lk.ijse.Bo.Custom.Impl.*;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBoFactory() {
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }

    public enum BoType {
        USER,COURSE,STUDENT,STUDENT_COURSE,PAYMENT
    }

    public SuperBo getBoType(BoType boType) {
        switch (boType) {
            case USER:
                return new UserBoImpl();
            case COURSE:
                return new CourseBoImpl();
            case STUDENT:
                return new StudentBoImpl();
            case STUDENT_COURSE:
                return new StudentCourseBoImpl();
            case PAYMENT:
                return new PaymentBoImpl();
            default:
                return null;
        }
    }
}
