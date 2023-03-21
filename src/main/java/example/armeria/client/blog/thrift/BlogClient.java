package example.armeria.client.blog.thrift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.client.thrift.ThriftClients;
import com.linecorp.armeria.common.thrift.ThriftFuture;

import example.armeria.blog.thrift.BlogPost;
import example.armeria.blog.thrift.BlogService;
import example.armeria.blog.thrift.CreateBlogPostRequest;

import example.armeria.blog.thrift.BlogNotFoundException;
import example.armeria.blog.thrift.GetBlogPostRequest;

import example.armeria.blog.thrift.ListBlogPostsRequest;
import example.armeria.blog.thrift.ListBlogPostsResponse;

import com.linecorp.armeria.client.logging.LoggingRpcClient;

import example.armeria.blog.thrift.UpdateBlogPostRequest;

import example.armeria.blog.thrift.DeleteBlogPostRequest;

public class BlogClient {
    private static final Logger logger = LoggerFactory.getLogger(BlogClient.class);
    static BlogService.Iface blogService;
    public static void main(String[] args) throws Exception {
        blogService = ThriftClients.builder("http://127.0.0.1:8080")
                            .path("/thrift")
                            //.responseTimeoutMillis(10000)
                            //.rpcDecorator(LoggingRpcClient.newDecorator()) // add this
                            .build(BlogService.Iface.class);
        
        
        String greeting = blogService.hello("Armerian World");
        logger.info(greeting);

        BlogClient blogClient = new BlogClient();
        blogClient.testRun();
    }

    void testRun() throws Exception {
        createBlogPost("My first blog", "Yay");
        //createBlogPost("Another blog post", "Creating a post via createBlogPost().");
        //deleteBlogPost(0);
        //listBlogPosts();
        //getBlogPost(1);
        //updateBlogPost(1, "New title", "New content.");
        //getBlogPost(1);
        //updateBlogPost(10000, "New title", "New content."); // add this
    }

    void createBlogPost(String title, String content) throws Exception {
        final CreateBlogPostRequest request = new CreateBlogPostRequest().setTitle(title)
                                                                        .setContent(content);

        final BlogPost response = blogService.createBlogPost(request);;
        logger.info("[Create response] Title: {} Content: {}", response.getTitle(), response.getContent());
    }

    void getBlogPost(int id) throws Exception {
        final GetBlogPostRequest request = new GetBlogPostRequest().setId(id);
        blogService.getBlogPost(request);
    }

    void listBlogPosts() throws Exception {
        final ListBlogPostsRequest request = new ListBlogPostsRequest().setDescending(false);
        blogService.listBlogPosts(request);
    }

    void updateBlogPost(int id, String newTitle, String newContent) throws Exception {
        final UpdateBlogPostRequest request = new UpdateBlogPostRequest().setId(id).setTitle(newTitle).setContent(newContent);
        blogService.updateBlogPost(request);
    }

    void deleteBlogPost(int id) throws Exception {
        final DeleteBlogPostRequest request = new DeleteBlogPostRequest().setId(id);
        blogService.deleteBlogPost(request);
    }
}
