//package sg.edu.nus.journybackend.mapper;
//
//import sg.edu.nus.journybackend.dto.PostDto;
//import sg.edu.nus.journybackend.entity.Post;
//
//public class PostMapper {
//    public static PostDto mapToPostDto(Post post) {
//        return new PostDto(
//                post.getPostId(),
//                post.getCreatedDateTime(),
//                post.getLikeCount(),
//                post.getKmlFile(),
//                post.getCreator(),
//                post.getComments()
//        );
//    }
//
//    public static Post mapToPost(PostDto postDto) {
//        return new Post(
//                postDto.getPostId(),
//                postDto.getCreatedDateTime(),
//                postDto.getLikeCount(),
//                postDto.getKmlFile(),
//                postDto.getCreator(),
//                postDto.getComments()
//        );
//    }
//}
