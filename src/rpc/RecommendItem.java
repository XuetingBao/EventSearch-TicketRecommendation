package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.Item;
import recommendation.GeoRecommendation;

/**
 * Servlet implementation class RecommendItem
 */
@WebServlet("/recommendation")
public class RecommendItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecommendItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
//		response.setContentType("application/json");
//		PrintWriter writer = response.getWriter();
//		
//		String username1 = request.getParameter("username1");
//		String username2 = request.getParameter("username2");
//		
//			
//		JSONArray array = new JSONArray();
//		if (request.getParameter("username1") != null && 
//				request.getParameter("username2") != null) {
//			try {
//				array.put(new JSONObject().put("name",username1));
//				array.put(new JSONObject().put("address","san francisco"));
//				array.put(new JSONObject().put("time","01/01/2019"));
//				
//				array.put(new JSONObject().put("name",username2));
//				array.put(new JSONObject().put("address","san jose"));
//				array.put(new JSONObject().put("time","02/02/2019"));
//			} catch(JSONException e) {
//				e.printStackTrace();
//			}
//			writer.print(array);
//		}
//		
//		
//		writer.close();
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}
		
		String userId = session.getAttribute("user_id").toString();
		// String userId = request.getParameter("user_id");

		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));

		GeoRecommendation recommendation = new GeoRecommendation();
		List<Item> items = recommendation.recommendItems(userId, lat, lon);
		JSONArray array = new JSONArray();
		for (Item item : items) {
			array.put(item.toJSONObject());
		}
		RpcHelper.writeJsonArray(response, array);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
