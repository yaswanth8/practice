package com.careerit.jfs.oops.statickeyword;

import java.util.List;

class LoginUser{
    private String user;
    private String password;

     public LoginUser(String user, String password) {
         this.user = user;
         this.password = password;
     }

     public String getUser() {
         return user;
     }

     public void setUser(String user) {
         this.user = user;
     }

     public String getPassword() {
         return password;
     }

     public void setPassword(String password) {
         this.password = password;
     }
 }
public class JavaArgs {
    public static void main(String[] args) {
        System.out.println("length"+args.length);

        List<LoginUser> list= List.of(new LoginUser("admin","pasword"),
                new LoginUser("Yaswanth","pass"));

        if(args.length==0){
            System.out.println("Provide username & password");
            return;
        }
        else
        {
            String username=args[0];
            String password=args[1];
            boolean isUserExist=list.stream().anyMatch(user -> user.getUser().equals(username) &&
                    user.getPassword().equals(password));
            if(isUserExist){
                System.out.println("Welcome"+username);
            }
            else{
                System.out.println("invalid usrename or password");
            }
        }
    }
}
