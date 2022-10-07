package com.example.Employee.api.model;


public enum EmployeeCategory {

   CATEGORYA,
   CATEGORYB,
   CATEGORYC,
   CATEGORYD;

   public static Boolean isValueisValueEnum(String category){
      // pour chaque caractère dans mon tableau d'enum
      for(EmployeeCategory caractère : EmployeeCategory.values()){
         // verifie si le carctère est bien une catégory 
         if(caractère.name().equals(category.toUpperCase())){
            return true;
         }
      }
      return false;
   }


}
    
    


