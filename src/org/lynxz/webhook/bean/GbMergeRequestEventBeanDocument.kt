package org.lynxz.webhook.bean

/**
 * Created by lynxz on 21/03/2017.
 * [merge 合并代码请求](https://docs.gitlab.com/ce/user/project/integrations/webhooks.html)
 * X-Gitlab-Event: Merge Request Hook
 */
class GbMergeRequestEventBeanDocument {

    /**
     * object_kind : merge_request
     * user : {"name":"Administrator","username":"root","avatar_url":"http://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=40&d=identicon"}
     * object_attributes : {"id":99,"target_branch":"master","source_branch":"ms-viewport","source_project_id":14,"author_id":51,"assignee_id":6,"title":"MS-Viewport","created_at":"2013-12-03T17:23:34Z","updated_at":"2013-12-03T17:23:34Z","st_commits":null,"st_diffs":null,"milestone_id":null,"state":"opened","merge_status":"unchecked","target_project_id":14,"iid":1,"description":"","source":{"name":"Awesome Project","description":"Aut reprehenderit ut est.","web_url":"http://example.com/awesome_space/awesome_project","avatar_url":null,"git_ssh_url":"git@example.com:awesome_space/awesome_project.git","git_http_url":"http://example.com/awesome_space/awesome_project.git","namespace":"Awesome Space","visibility_level":20,"path_with_namespace":"awesome_space/awesome_project","default_branch":"master","homepage":"http://example.com/awesome_space/awesome_project","url":"http://example.com/awesome_space/awesome_project.git","ssh_url":"git@example.com:awesome_space/awesome_project.git","http_url":"http://example.com/awesome_space/awesome_project.git"},"target":{"name":"Awesome Project","description":"Aut reprehenderit ut est.","web_url":"http://example.com/awesome_space/awesome_project","avatar_url":null,"git_ssh_url":"git@example.com:awesome_space/awesome_project.git","git_http_url":"http://example.com/awesome_space/awesome_project.git","namespace":"Awesome Space","visibility_level":20,"path_with_namespace":"awesome_space/awesome_project","default_branch":"master","homepage":"http://example.com/awesome_space/awesome_project","url":"http://example.com/awesome_space/awesome_project.git","ssh_url":"git@example.com:awesome_space/awesome_project.git","http_url":"http://example.com/awesome_space/awesome_project.git"},"last_commit":{"id":"da1560886d4f094c3e6c9ef40349f7d38b5d27d7","message":"fixed readme","timestamp":"2012-01-03T23:36:29+02:00","url":"http://example.com/awesome_space/awesome_project/commits/da1560886d4f094c3e6c9ef40349f7d38b5d27d7","author":{"name":"GitLab dev user","email":"gitlabdev@dv6700.(none)"}},"work_in_progress":false,"url":"http://example.com/diaspora/merge_requests/1","action":"open","assignee":{"name":"User1","username":"user1","avatar_url":"http://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=40&d=identicon"}}
     */

    var object_kind: String? = null
    var user: UserBean? = null
    var object_attributes: ObjectAttributesBean? = null

    class UserBean {
        /**
         * name : Administrator
         * username : root
         * avatar_url : http://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=40&d=identicon
         */

        var name: String? = null
        var username: String? = null
        var avatar_url: String? = null
    }

    class ObjectAttributesBean {
        /**
         * id : 99
         * target_branch : master
         * source_branch : ms-viewport
         * source_project_id : 14
         * author_id : 51
         * assignee_id : 6
         * title : MS-Viewport
         * created_at : 2013-12-03T17:23:34Z
         * updated_at : 2013-12-03T17:23:34Z
         * st_commits : null
         * st_diffs : null
         * milestone_id : null
         * state : opened
         * merge_status : unchecked
         * target_project_id : 14
         * iid : 1
         * description :
         * source : {"name":"Awesome Project","description":"Aut reprehenderit ut est.","web_url":"http://example.com/awesome_space/awesome_project","avatar_url":null,"git_ssh_url":"git@example.com:awesome_space/awesome_project.git","git_http_url":"http://example.com/awesome_space/awesome_project.git","namespace":"Awesome Space","visibility_level":20,"path_with_namespace":"awesome_space/awesome_project","default_branch":"master","homepage":"http://example.com/awesome_space/awesome_project","url":"http://example.com/awesome_space/awesome_project.git","ssh_url":"git@example.com:awesome_space/awesome_project.git","http_url":"http://example.com/awesome_space/awesome_project.git"}
         * target : {"name":"Awesome Project","description":"Aut reprehenderit ut est.","web_url":"http://example.com/awesome_space/awesome_project","avatar_url":null,"git_ssh_url":"git@example.com:awesome_space/awesome_project.git","git_http_url":"http://example.com/awesome_space/awesome_project.git","namespace":"Awesome Space","visibility_level":20,"path_with_namespace":"awesome_space/awesome_project","default_branch":"master","homepage":"http://example.com/awesome_space/awesome_project","url":"http://example.com/awesome_space/awesome_project.git","ssh_url":"git@example.com:awesome_space/awesome_project.git","http_url":"http://example.com/awesome_space/awesome_project.git"}
         * last_commit : {"id":"da1560886d4f094c3e6c9ef40349f7d38b5d27d7","message":"fixed readme","timestamp":"2012-01-03T23:36:29+02:00","url":"http://example.com/awesome_space/awesome_project/commits/da1560886d4f094c3e6c9ef40349f7d38b5d27d7","author":{"name":"GitLab dev user","email":"gitlabdev@dv6700.(none)"}}
         * work_in_progress : false
         * url : http://example.com/diaspora/merge_requests/1
         * action : open
         * assignee : {"name":"User1","username":"user1","avatar_url":"http://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=40&d=identicon"}
         */

        var id: Int = 0
        var target_branch: String? = null
        var source_branch: String? = null
        var source_project_id: Int = 0
        var author_id: Int = 0
        var assignee_id: Int = 0
        var title: String? = null
        var created_at: String? = null
        var updated_at: String? = null
        var st_commits: Any? = null
        var st_diffs: Any? = null
        var milestone_id: Any? = null
        var state: String? = null
        var merge_status: String? = null
        var target_project_id: Int = 0
        var iid: Int = 0
        var description: String? = null
        var source: SourceBean? = null
        var target: TargetBean? = null
        var last_commit: LastCommitBean? = null
        var isWork_in_progress: Boolean = false
        var url: String? = null
        var action: String? = null
        var assignee: AssigneeBean? = null

        class SourceBean {
            /**
             * name : Awesome Project
             * description : Aut reprehenderit ut est.
             * web_url : http://example.com/awesome_space/awesome_project
             * avatar_url : null
             * git_ssh_url : git@example.com:awesome_space/awesome_project.git
             * git_http_url : http://example.com/awesome_space/awesome_project.git
             * namespace : Awesome Space
             * visibility_level : 20
             * path_with_namespace : awesome_space/awesome_project
             * default_branch : master
             * homepage : http://example.com/awesome_space/awesome_project
             * url : http://example.com/awesome_space/awesome_project.git
             * ssh_url : git@example.com:awesome_space/awesome_project.git
             * http_url : http://example.com/awesome_space/awesome_project.git
             */

            var name: String? = null
            var description: String? = null
            var web_url: String? = null
            var avatar_url: Any? = null
            var git_ssh_url: String? = null
            var git_http_url: String? = null
            var namespace: String? = null
            var visibility_level: Int = 0
            var path_with_namespace: String? = null
            var default_branch: String? = null
            var homepage: String? = null
            var url: String? = null
            var ssh_url: String? = null
            var http_url: String? = null
        }

        class TargetBean {
            /**
             * name : Awesome Project
             * description : Aut reprehenderit ut est.
             * web_url : http://example.com/awesome_space/awesome_project
             * avatar_url : null
             * git_ssh_url : git@example.com:awesome_space/awesome_project.git
             * git_http_url : http://example.com/awesome_space/awesome_project.git
             * namespace : Awesome Space
             * visibility_level : 20
             * path_with_namespace : awesome_space/awesome_project
             * default_branch : master
             * homepage : http://example.com/awesome_space/awesome_project
             * url : http://example.com/awesome_space/awesome_project.git
             * ssh_url : git@example.com:awesome_space/awesome_project.git
             * http_url : http://example.com/awesome_space/awesome_project.git
             */

            var name: String? = null
            var description: String? = null
            var web_url: String? = null
            var avatar_url: Any? = null
            var git_ssh_url: String? = null
            var git_http_url: String? = null
            var namespace: String? = null
            var visibility_level: Int = 0
            var path_with_namespace: String? = null
            var default_branch: String? = null
            var homepage: String? = null
            var url: String? = null
            var ssh_url: String? = null
            var http_url: String? = null
        }

        class LastCommitBean {
            /**
             * id : da1560886d4f094c3e6c9ef40349f7d38b5d27d7
             * message : fixed readme
             * timestamp : 2012-01-03T23:36:29+02:00
             * url : http://example.com/awesome_space/awesome_project/commits/da1560886d4f094c3e6c9ef40349f7d38b5d27d7
             * author : {"name":"GitLab dev user","email":"gitlabdev@dv6700.(none)"}
             */

            var id: String? = null
            var message: String? = null
            var timestamp: String? = null
            var url: String? = null
            var author: AuthorBean? = null

            class AuthorBean {
                /**
                 * name : GitLab dev user
                 * email : gitlabdev@dv6700.(none)
                 */

                var name: String? = null
                var email: String? = null
            }
        }

        class AssigneeBean {
            /**
             * name : User1
             * username : user1
             * avatar_url : http://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=40&d=identicon
             */

            var name: String? = null
            var username: String? = null
            var avatar_url: String? = null
        }
    }
}
