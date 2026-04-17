package com.ingcollegeapt.week7twebapp.controller.servlets;

import com.ingcollegeapt.week7twebapp.dao.EntryDAO;
import com.ingcollegeapt.week7twebapp.dao.TopicDAO;
import com.ingcollegeapt.week7twebapp.model.Entry;
import com.ingcollegeapt.week7twebapp.model.Topic;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "EntryServlet", urlPatterns = {"/entry"})
public class EntryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Read the topic parameter from the get request
        int topicId = Integer.valueOf(request.getParameter("topicid"));
        //if no action parameter Null Pointer exception so handle it
        final String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "new": {
                //Send the entry data to the entrylist.jsp entries and topic 
                Topic topic = new TopicDAO().getTopicDetails(topicId);
                //add the respective topic to the request attribute
                request.setAttribute("topic", topic);
                final RequestDispatcher rd = request.getRequestDispatcher("/pages/entryadd.jsp");
                rd.forward(request, response);
                break;
            }
            case "edit": { // this adds local scope to variables inside it

                break;
            }
            default: {
                //Get EntryDAO to perform CRUD operation on entry for topic
                EntryDAO edao = new EntryDAO();

                //Fetch all the entries for the specific topic
                ArrayList<Entry> entries = edao.fetchAllEntries(topicId);

                //Send the entry data to the entrylist.jsp entries and topic 
                Topic topic = new TopicDAO().getTopicDetails(topicId);

                //add the respective topic and entries to the request attribute
                request.setAttribute("topic", topic);
                request.setAttribute("entries", entries);

                //dispatch this data to entrylist and display them
                //Dispatcher is server side so no url change will be seen
                final RequestDispatcher rd = request.getRequestDispatcher("/pages/entrylist.jsp");
                rd.forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Read the topic parameter from the post request
        int topicId = Integer.valueOf(request.getParameter("topicid"));
        System.out.println("The topic id selected is: " + topicId);
        //if no action parameter Null Pointer exception so handle it
        final String action = request.getParameter("action") == null ? "" : request.getParameter("action");
        switch (action) {
            case "add": {
                //TODO: check user if topic matches for user after session management

                //Read all parameters
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                String link = request.getParameter("link");
                //File will be saved in folder and path will be saved in database
                String img_path = request.getParameter("image");
                //Create entry to pass to DAO method
                Entry entry = new Entry();
                entry.setId(topicId);
                entry.setTitle(title);
                entry.setDescription(description);
                entry.setLink(link);
                entry.setImage(img_path);

                //Check if title is not blank or empty
                boolean notblackTitle = !title.isBlank();

                //Get EntryDAO to perform CRUD operation on entry for entry
                EntryDAO edao = new EntryDAO();
                boolean isAdded = false;
                if (notblackTitle) {
                    isAdded = edao.insertEntry(entry, topicId);
                }
                //If added display the added entry else show error
                if (isAdded) {
                    //new request cycle so we again send topicId
                    response.sendRedirect(request.getContextPath() + "/entry?topicid=" + topicId);
                } else {
                    //Send the topic data
                    Topic topic = new TopicDAO().getTopicDetails(topicId);
                    log("The topic name is " + topic.getName());
                    //add the respective topic to the request attribute
                    request.setAttribute("topic", topic);
                    request.setAttribute("error", "Error or Title is blank!");
                    //Dispatcher is server side so no url change will be seen
                    final RequestDispatcher rd = request.getRequestDispatcher("/pages/entryadd.jsp");
                    rd.forward(request, response);
                }
                break;
            }
            case "delete": {

                break;
            }
            default:

        }
    }

}
