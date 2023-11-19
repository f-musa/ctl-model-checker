package ctl.ctlformula;

/**
 * Created by  TETEREOU Aboudourazakou   on  11/8/2023
 * Project name ctl-model-checker
 */
public class Bool implements   Formula {

    Boolean isVerified = false;

    public Bool(Boolean isVerified){
        this.isVerified = isVerified;
    }




        public String toString() {
            return this.getClass().getSimpleName()+"("+isVerified+")";
        }



    @Override
    public Boolean getIsVerified() {
        return isVerified;
    }
}
