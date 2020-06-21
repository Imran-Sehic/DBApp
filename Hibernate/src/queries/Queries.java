package queries;

import hibernate.HibernateUtil;
import hibernate.Workers;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Queries {
    
    public static List<Workers> getEmployees(){
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;
        
        List<Workers> list = null;
        
        try{
            tx = session.beginTransaction();
            list = session.createSQLQuery("select * from workers").addEntity(Workers.class).list();
            tx.commit();
        }catch(HibernateException e){
            if(tx != null){
                tx.rollback();
            }
            System.out.println(e);
        }finally{
            HibernateUtil.close();
        }
        return list;
    }
    
    public static void showEmployees(JTable table){
        List<Workers> lista = getEmployees();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object[] row = new Object[5];
        int count = model.getRowCount();
        for(int i=count-1; i>=0; i--){
            model.removeRow(i);
        }
        for(int i=0; i<lista.size(); i++){
            row[0] = lista.get(i).getId();
            row[1] = lista.get(i).getName();
            row[2] = lista.get(i).getAge();
            row[3] = lista.get(i).getAddress();
            row[4] = lista.get(i).getSalary();
            model.addRow(row);
        }
    }
    
    public static void deleteEmployee(boolean isId, int id){
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;

        try {
        tx = session.beginTransaction();
        
        if(isId){
            Workers worker = (Workers)session.get(Workers.class, id);
            if(worker != null){
                session.delete(worker);
                JOptionPane.showMessageDialog(null, "Successfully deleted");
            }else{
                JOptionPane.showMessageDialog(null, "No employee with given Id was found in the database");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please enter a valid Id");
        }
        
        tx.commit();
        } catch (HibernateException e) {
        if (tx != null) {
        tx.rollback();
        }
        System.out.println(e);
        } finally {
        HibernateUtil.close();
        }
    }
    
    public static void updateEmployee(String ageText, String salaryText, boolean isId, boolean isAge, boolean isSalary, int id, String name, int age, String address, int salary){
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;

        try {
        tx = session.beginTransaction();
        
        if(isId){
            Workers worker = (Workers)session.get(Workers.class, id);
            if(worker != null){
                if(isAge){
                    worker.setAge(age);
                }else if(!ageText.trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Enter a valid age value");
                    return;
                }
                if(isSalary){
                    worker.setSalary(salary);
                }else if(!salaryText.trim().equals("")){
                    JOptionPane.showMessageDialog(null, "Enter a valid salary value");
                    return;
                }
                if(!name.trim().equals("")){
                    worker.setName(name);
                }
                if(!address.trim().equals("")){
                    worker.setAddress(address);
                }
                
                if(ageText.trim().equals("") && salaryText.trim().equals("") && name.trim().equals("") && address.trim().equals("")){
                    JOptionPane.showMessageDialog(null, "No values to update for given Id");
                }else{
                    session.update(worker);
                    JOptionPane.showMessageDialog(null, "Successfully updated");
                }
            }else{
                JOptionPane.showMessageDialog(null, "No employee with given Id was found");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Enter a valid Id");
        }
        
        tx.commit();
        } catch (HibernateException e) {
        if (tx != null) {
        tx.rollback();
        }
        System.out.println(e);
        } finally {
        HibernateUtil.close();
        }
    }
    
    public static void insertEmployee(Workers worker){
        Session session = HibernateUtil.createSessionFactory().openSession();
        Transaction tx = null;

        try {
        tx = session.beginTransaction();
        
        session.persist(worker);
        
        tx.commit();
        } catch (HibernateException e) {
        if (tx != null) {
        tx.rollback();
        }
        System.out.println(e);
        } finally {
        HibernateUtil.close();
        }
    }
    
    public static void showEmployeesByName(JTable table, String name){
        List<Workers> lista = getEmployees();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object[] row = new Object[5];
        int count = model.getRowCount();
        for(int i=count-1; i>=0; i--){
            model.removeRow(i);
        }
        for(int i=0; i<lista.size(); i++){
            if(name.equals(lista.get(i).getName())){
                row[0] = lista.get(i).getId();
                row[1] = lista.get(i).getName();
                row[2] = lista.get(i).getAge();
                row[3] = lista.get(i).getAddress();
                row[4] = lista.get(i).getSalary();
                model.addRow(row);
            }
        }
    }
    
    public static void showEmployeesByAge(JTable table, String age){
        List<Workers> lista = getEmployees();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object[] row = new Object[5];
        int count = model.getRowCount();
        for(int i=count-1; i>=0; i--){
            model.removeRow(i);
        }
        for(int i=0; i<lista.size(); i++){
            if(age.equals(String.valueOf(lista.get(i).getAge()))){
                row[0] = lista.get(i).getId();
                row[1] = lista.get(i).getName();
                row[2] = lista.get(i).getAge();
                row[3] = lista.get(i).getAddress();
                row[4] = lista.get(i).getSalary();
                model.addRow(row);
            }
        }
    }
    
    public static void showEmployeesByAddress(JTable table, String address){
        List<Workers> lista = getEmployees();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object[] row = new Object[5];
        int count = model.getRowCount();
        for(int i=count-1; i>=0; i--){
            model.removeRow(i);
        }
        for(int i=0; i<lista.size(); i++){
            if(address.equals(lista.get(i).getAddress())){
                row[0] = lista.get(i).getId();
                row[1] = lista.get(i).getName();
                row[2] = lista.get(i).getAge();
                row[3] = lista.get(i).getAddress();
                row[4] = lista.get(i).getSalary();
                model.addRow(row);
            }
        }
    }
    
    public static void showEmployeesBySalary(JTable table, String salary){
        List<Workers> lista = getEmployees();
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        Object[] row = new Object[5];
        int count = model.getRowCount();
        for(int i=count-1; i>=0; i--){
            model.removeRow(i);
        }
        for(int i=0; i<lista.size(); i++){
            if(salary.equals(String.valueOf(lista.get(i).getSalary()))){
                row[0] = lista.get(i).getId();
                row[1] = lista.get(i).getName();
                row[2] = lista.get(i).getAge();
                row[3] = lista.get(i).getAddress();
                row[4] = lista.get(i).getSalary();
                model.addRow(row);
            }
        }
    }
}
