// package Lib;

// public class test2 {
//   public static void main(String[] args) {
//     try {
//       // processRequest(request, response);
//       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//       // tao doi tuong connection
//       Connection conn = DriverManager.getConnection(
//           "jdbc:sqlserver://DELL:1433;databaseName=se1705;user=sa;password=admin;encrypt=true;trustServerCertificate=true;");
//       // tao doi tuong truy van du lieu
//       Statement st = conn.createStatement();
//       // tao cau lenh truy van
//       String sql = "";
//       sql = " INSERT INTO sinhvien(id, fullname, birthdate, gender,  address) VALUES (5, 'nguoi thu 5', '2003-03-03', 'male', 'hoa tan');";
//       // thuc thi cau lenh truy van va luu ket qua
//       ResultSet rs = st.executeQuery(sql);
//       while (rs.next()) {
//         System.out.println(rs.getInt("id"));
//         System.out.println(rs.getString("fullname"));
//         System.out.println(rs.getDate("birthdate"));
//         System.out.println(rs.getString("gender"));
//         System.out.println(rs.getString("address"));
//       }
//     } catch (SQLException ex) {
//       Logger.getLogger(data.class.getName()).log(Level.SEVERE, null, ex);

//     } catch (ClassNotFoundException ex) {
//       Logger.getLogger(data.class.getName()).log(Level.SEVERE, null, ex);
//     }
//   }
// }
