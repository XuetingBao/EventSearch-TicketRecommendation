package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.TicketMasterAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		/* ***** Method1: HTML format data ***** */
		
//		PrintWriter writer = response.getWriter();
//		if (request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			writer.println("<html><body>");
//			writer.println("<h1>Hello " + username + "</h1>");
//			writer.println("</body></html>");
//		} else {
//			writer.println("<html><body>");
//			writer.println("<h1>Hello Stranger</h1>");
//			writer.println("</body></html>");
//		}
		
		
		/* ***** Method2: JSON Object (returns with "{}") ***** */
		
//		response.setContentType("application/json");
//		PrintWriter writer = response.getWriter();
//		if (request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			
//			JSONObject obj = new JSONObject();
//			try {
//				obj.put("username", username);
//			} catch(JSONException e) {
//				e.printStackTrace();
//			}
//			writer.print(obj);
//		} else {
//			
//		}
		
//		/* ***** Method2 for array (returns with "[]")***** */
//		response.setContentType("application/json");
//		PrintWriter writer = response.getWriter();
//		if (request.getParameter("username") != null) {
//			String username = request.getParameter("username");
//			
//			JSONArray array = new JSONArray();
//			try {
//				array.put(new JSONObject().put("username",username));
//				array.put(new JSONObject().put("Nice to meet you.",username));
//			} catch(JSONException e) {
//				e.printStackTrace();
//			}
//			writer.print(array);
//		} else {
//			JSONArray array = new JSONArray();
//			try {
//				array.put(new JSONObject().put("username","Stranger"));
//				array.put(new JSONObject().put("Nice to meet you.", "Stranger"));
//			} catch(JSONException e) {
//				e.printStackTrace();
//			}
//			writer.print(array);
//		}
//		
//		writer.close();
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		
		String userId = session.getAttribute("user_id").toString();
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		String term = request.getParameter("term");
		
		DBConnection connection = DBConnectionFactory.getConnection();
        try {
        	List<Item> items = connection.searchItems(lat, lon, term);
        	Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);
        	
        	JSONArray array = new JSONArray();
        	for (Item item : items) {
        		JSONObject obj = item.toJSONObject();
				obj.put("favorite", favoritedItemIds.contains(item.getItemId()));
				array.put(obj);

        	}
        	RpcHelper.writeJsonArray(response, array);

         } catch (Exception e) {
        	 e.printStackTrace();
         } finally {
        	 connection.close();
         }


		
//		TicketMasterAPI tmAPI = new TicketMasterAPI();
//		List<Item> items = tmAPI.search(lat, lon, null);
//		
//		JSONArray array = new JSONArray();
//		for (Item item : items) {
//			array.put(item.toJSONObject());
//		}
//		RpcHelper.writeJsonArray(response, array);

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
