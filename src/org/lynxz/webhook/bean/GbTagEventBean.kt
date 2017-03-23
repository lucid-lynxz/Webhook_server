package org.lynxz.webhook.bean

/**
 * Created by lynxz on 21/03/2017.
 * [Gitlab tag event 通知实体](https://docs.gitlab.com/ce/user/project/integrations/webhooks.html)
 * Request header:      X-Gitlab-Event: Tag Push Hook
 */
class GbTagEventBean {
    /**
     * object_kind : tag_push
     * before : 0000000000000000000000000000000000000000
     * after : 82b3d5ae55f7080f1e6022629cdb57bfae7cccc7
     * ref : refs/tags/v1.0.0
     * checkout_sha : 82b3d5ae55f7080f1e6022629cdb57bfae7cccc7
     * user_id : 1
     * user_name : John Smith
     * user_avatar : https://s.gravatar.com/avatar/d4c74594d841139328695756648b6bd6?s=8://s.gravatar.com/avatar/d4c74594d841139328695756648b6bd6?s=80
     * project_id : 1
     * project : {"name":"Example","description":"","web_url":"http://example.com/jsmith/example","avatar_url":null,"git_ssh_url":"git@example.com:jsmith/example.git","git_http_url":"http://example.com/jsmith/example.git","namespace":"Jsmith","visibility_level":0,"path_with_namespace":"jsmith/example","default_branch":"master","homepage":"http://example.com/jsmith/example","url":"git@example.com:jsmith/example.git","ssh_url":"git@example.com:jsmith/example.git","http_url":"http://example.com/jsmith/example.git"}
     * repository : {"name":"Example","url":"ssh://git@example.com/jsmith/example.git","description":"","homepage":"http://example.com/jsmith/example","git_http_url":"http://example.com/jsmith/example.git","git_ssh_url":"git@example.com:jsmith/example.git","visibility_level":0}
     * commits : []
     * total_commits_count : 0
     */
    var object_kind: String? = null
    var before: String? = null
    var after: String? = null
    var ref: String? = null
    var checkout_sha: String? = null
    var user_id: Long = 0
    var user_name: String? = null
    var user_avatar: String? = null
    var project_id: Long = 0
    var project: ProjectBean? = null
    var repository: RepositoryBean? = null
    var total_commits_count: Int = 0
    var commits: List<*>? = null

    class ProjectBean {
        /**
         * name : Example
         * description :
         * web_url : http://example.com/jsmith/example
         * avatar_url : null
         * git_ssh_url : git@example.com:jsmith/example.git
         * git_http_url : http://example.com/jsmith/example.git
         * namespace : Jsmith
         * visibility_level : 0
         * path_with_namespace : jsmith/example
         * default_branch : master
         * homepage : http://example.com/jsmith/example
         * url : git@example.com:jsmith/example.git
         * ssh_url : git@example.com:jsmith/example.git
         * http_url : http://example.com/jsmith/example.git
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

    class RepositoryBean {
        /**
         * name : Example
         * url : ssh://git@example.com/jsmith/example.git
         * description :
         * homepage : http://example.com/jsmith/example
         * git_http_url : http://example.com/jsmith/example.git
         * git_ssh_url : git@example.com:jsmith/example.git
         * visibility_level : 0
         */

        var name: String? = null
        var url: String? = null
        var description: String? = null
        var homepage: String? = null
        var git_http_url: String? = null
        var git_ssh_url: String? = null
        var visibility_level: Int = 0
    }
}
