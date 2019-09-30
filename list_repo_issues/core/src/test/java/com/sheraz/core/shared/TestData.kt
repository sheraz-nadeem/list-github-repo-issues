package com.sheraz.core.shared

import com.google.gson.Gson
import com.sheraz.core.data.db.entity.GitHubRepoEntity
import com.sheraz.core.data.db.entity.GitHubRepoIssueEntity

val tensorflowModelsRepo: GitHubRepoEntity = Gson().fromJson(
    "{\n" +
            "      \"id\": 51117837,\n" +
            "      \"node_id\": \"MDEwOlJlcG9zaXRvcnk1MTExNzgzNw==\",\n" +
            "      \"name\": \"models\",\n" +
            "      \"full_name\": \"tensorflow/models\",\n" +
            "      \"private\": false,\n" +
            "      \"owner\": {\n" +
            "        \"login\": \"tensorflow\",\n" +
            "        \"id\": 15658638,\n" +
            "        \"node_id\": \"MDEyOk9yZ2FuaXphdGlvbjE1NjU4NjM4\",\n" +
            "        \"avatar_url\": \"https://avatars1.githubusercontent.com/u/15658638?v=4\",\n" +
            "        \"gravatar_id\": \"\",\n" +
            "        \"url\": \"https://api.github.com/users/tensorflow\",\n" +
            "        \"html_url\": \"https://github.com/tensorflow\",\n" +
            "        \"followers_url\": \"https://api.github.com/users/tensorflow/followers\",\n" +
            "        \"following_url\": \"https://api.github.com/users/tensorflow/following{/other_user}\",\n" +
            "        \"gists_url\": \"https://api.github.com/users/tensorflow/gists{/gist_id}\",\n" +
            "        \"starred_url\": \"https://api.github.com/users/tensorflow/starred{/owner}{/repo}\",\n" +
            "        \"subscriptions_url\": \"https://api.github.com/users/tensorflow/subscriptions\",\n" +
            "        \"organizations_url\": \"https://api.github.com/users/tensorflow/orgs\",\n" +
            "        \"repos_url\": \"https://api.github.com/users/tensorflow/repos\",\n" +
            "        \"events_url\": \"https://api.github.com/users/tensorflow/events{/privacy}\",\n" +
            "        \"received_events_url\": \"https://api.github.com/users/tensorflow/received_events\",\n" +
            "        \"type\": \"Organization\",\n" +
            "        \"site_admin\": false\n" +
            "      },\n" +
            "      \"html_url\": \"https://github.com/tensorflow/models\",\n" +
            "      \"description\": \"Models and examples built with TensorFlow\",\n" +
            "      \"fork\": false,\n" +
            "      \"url\": \"https://api.github.com/repos/tensorflow/models\",\n" +
            "      \"forks_url\": \"https://api.github.com/repos/tensorflow/models/forks\",\n" +
            "      \"keys_url\": \"https://api.github.com/repos/tensorflow/models/keys{/key_id}\",\n" +
            "      \"collaborators_url\": \"https://api.github.com/repos/tensorflow/models/collaborators{/collaborator}\",\n" +
            "      \"teams_url\": \"https://api.github.com/repos/tensorflow/models/teams\",\n" +
            "      \"hooks_url\": \"https://api.github.com/repos/tensorflow/models/hooks\",\n" +
            "      \"issue_events_url\": \"https://api.github.com/repos/tensorflow/models/issues/events{/number}\",\n" +
            "      \"events_url\": \"https://api.github.com/repos/tensorflow/models/events\",\n" +
            "      \"assignees_url\": \"https://api.github.com/repos/tensorflow/models/assignees{/user}\",\n" +
            "      \"branches_url\": \"https://api.github.com/repos/tensorflow/models/branches{/branch}\",\n" +
            "      \"tags_url\": \"https://api.github.com/repos/tensorflow/models/tags\",\n" +
            "      \"blobs_url\": \"https://api.github.com/repos/tensorflow/models/git/blobs{/sha}\",\n" +
            "      \"git_tags_url\": \"https://api.github.com/repos/tensorflow/models/git/tags{/sha}\",\n" +
            "      \"git_refs_url\": \"https://api.github.com/repos/tensorflow/models/git/refs{/sha}\",\n" +
            "      \"trees_url\": \"https://api.github.com/repos/tensorflow/models/git/trees{/sha}\",\n" +
            "      \"statuses_url\": \"https://api.github.com/repos/tensorflow/models/statuses/{sha}\",\n" +
            "      \"languages_url\": \"https://api.github.com/repos/tensorflow/models/languages\",\n" +
            "      \"stargazers_url\": \"https://api.github.com/repos/tensorflow/models/stargazers\",\n" +
            "      \"contributors_url\": \"https://api.github.com/repos/tensorflow/models/contributors\",\n" +
            "      \"subscribers_url\": \"https://api.github.com/repos/tensorflow/models/subscribers\",\n" +
            "      \"subscription_url\": \"https://api.github.com/repos/tensorflow/models/subscription\",\n" +
            "      \"commits_url\": \"https://api.github.com/repos/tensorflow/models/commits{/sha}\",\n" +
            "      \"git_commits_url\": \"https://api.github.com/repos/tensorflow/models/git/commits{/sha}\",\n" +
            "      \"comments_url\": \"https://api.github.com/repos/tensorflow/models/comments{/number}\",\n" +
            "      \"issue_comment_url\": \"https://api.github.com/repos/tensorflow/models/issues/comments{/number}\",\n" +
            "      \"contents_url\": \"https://api.github.com/repos/tensorflow/models/contents/{+path}\",\n" +
            "      \"compare_url\": \"https://api.github.com/repos/tensorflow/models/compare/{base}...{head}\",\n" +
            "      \"merges_url\": \"https://api.github.com/repos/tensorflow/models/merges\",\n" +
            "      \"archive_url\": \"https://api.github.com/repos/tensorflow/models/{archive_format}{/ref}\",\n" +
            "      \"downloads_url\": \"https://api.github.com/repos/tensorflow/models/downloads\",\n" +
            "      \"issues_url\": \"https://api.github.com/repos/tensorflow/models/issues{/number}\",\n" +
            "      \"pulls_url\": \"https://api.github.com/repos/tensorflow/models/pulls{/number}\",\n" +
            "      \"milestones_url\": \"https://api.github.com/repos/tensorflow/models/milestones{/number}\",\n" +
            "      \"notifications_url\": \"https://api.github.com/repos/tensorflow/models/notifications{?since,all,participating}\",\n" +
            "      \"labels_url\": \"https://api.github.com/repos/tensorflow/models/labels{/name}\",\n" +
            "      \"releases_url\": \"https://api.github.com/repos/tensorflow/models/releases{/id}\",\n" +
            "      \"deployments_url\": \"https://api.github.com/repos/tensorflow/models/deployments\",\n" +
            "      \"created_at\": \"2016-02-05T01:15:20Z\",\n" +
            "      \"updated_at\": \"2019-09-30T10:49:37Z\",\n" +
            "      \"pushed_at\": \"2019-09-30T00:39:24Z\",\n" +
            "      \"git_url\": \"git://github.com/tensorflow/models.git\",\n" +
            "      \"ssh_url\": \"git@github.com:tensorflow/models.git\",\n" +
            "      \"clone_url\": \"https://github.com/tensorflow/models.git\",\n" +
            "      \"svn_url\": \"https://github.com/tensorflow/models\",\n" +
            "      \"homepage\": \"\",\n" +
            "      \"size\": 522842,\n" +
            "      \"stargazers_count\": 57930,\n" +
            "      \"watchers_count\": 57930,\n" +
            "      \"language\": \"Python\",\n" +
            "      \"has_issues\": true,\n" +
            "      \"has_projects\": true,\n" +
            "      \"has_downloads\": true,\n" +
            "      \"has_wiki\": true,\n" +
            "      \"has_pages\": false,\n" +
            "      \"forks_count\": 36427,\n" +
            "      \"mirror_url\": null,\n" +
            "      \"archived\": false,\n" +
            "      \"disabled\": false,\n" +
            "      \"open_issues_count\": 1900,\n" +
            "      \"license\": {\n" +
            "        \"key\": \"apache-2.0\",\n" +
            "        \"name\": \"Apache License 2.0\",\n" +
            "        \"spdx_id\": \"Apache-2.0\",\n" +
            "        \"url\": \"https://api.github.com/licenses/apache-2.0\",\n" +
            "        \"node_id\": \"MDc6TGljZW5zZTI=\"\n" +
            "      },\n" +
            "      \"forks\": 36427,\n" +
            "      \"open_issues\": 1900,\n" +
            "      \"watchers\": 57930,\n" +
            "      \"default_branch\": \"master\",\n" +
            "      \"score\": 100.940636\n" +
            "    }", GitHubRepoEntity::class.java
)

val tensorflowModelsRepoIssue1: GitHubRepoIssueEntity = Gson().fromJson(
    "{\n" +
            "    \"url\": \"https://api.github.com/repos/tensorflow/models/issues/7612\",\n" +
            "    \"repository_url\": \"https://api.github.com/repos/tensorflow/models\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/tensorflow/models/issues/7612/labels{/name}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/tensorflow/models/issues/7612/comments\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/tensorflow/models/issues/7612/events\",\n" +
            "    \"html_url\": \"https://github.com/tensorflow/models/issues/7612\",\n" +
            "    \"id\": 499852028,\n" +
            "    \"node_id\": \"MDU6SXNzdWU0OTk4NTIwMjg=\",\n" +
            "    \"number\": 7612,\n" +
            "    \"title\": \"BERT 2.0 break the fusion of MatMul and BiasAdd\",\n" +
            "    \"user\": {\n" +
            "      \"login\": \"Zantares\",\n" +
            "      \"id\": 38638514,\n" +
            "      \"node_id\": \"MDQ6VXNlcjM4NjM4NTE0\",\n" +
            "      \"avatar_url\": \"https://avatars0.githubusercontent.com/u/38638514?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/Zantares\",\n" +
            "      \"html_url\": \"https://github.com/Zantares\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/Zantares/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/Zantares/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/Zantares/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/Zantares/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/Zantares/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/Zantares/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/Zantares/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/Zantares/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/Zantares/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"labels\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"state\": \"open\",\n" +
            "    \"locked\": false,\n" +
            "    \"assignee\": null,\n" +
            "    \"assignees\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"milestone\": null,\n" +
            "    \"comments\": 3,\n" +
            "    \"created_at\": \"2019-09-29T03:42:40Z\",\n" +
            "    \"updated_at\": \"2019-09-30T02:45:49Z\",\n" +
            "    \"closed_at\": null,\n" +
            "    \"author_association\": \"NONE\",\n" +
            "    \"body\": \"### System information\\r\\n- **What is the top-level directory of the model you are using**: tensorflow/models\\r\\n- **Have I written custom code (as opposed to using a stock example script provided in TensorFlow)**: no\\r\\n- **OS Platform and Distribution (e.g., Linux Ubuntu 16.04)**: Red Hat 4.8.5-16\\r\\n- **TensorFlow installed from (source or binary)**: source build\\r\\n- **TensorFlow version (use command below)**: 1.4, master branch, commit id is 9e4cfea27ea023150d9d067738da7d23f9c80844\\r\\n- **Bazel version (if compiling from source)**: 0.24.1\\r\\n- **CUDA/cuDNN version**: NA\\r\\n- **GPU model and memory**: NA\\r\\n- **Exact command to reproduce**:\\r\\npython official/nlp/bert/run_classifier.py --data_dir=/home/tenglu/model/XNLI-1.0 --output_dir=/home/tenglu/source/intel-models/benchmarks/common/tensorflow/logs --vocab_file=/home/tenglu/model/chinese_L-12_H-768_A-12/vocab.txt --bert_config_file=/home/tenglu/model/chinese_L-12_H-768_A-12/bert_config.json --init_checkpoint=/home/tenglu/model/chinese_L-12_H-768_A-12/bert_model.ckpt --task_name=XNLI --max_seq_length=128 --eval_batch_size=8 --learning_rate=5e-05 --do_train=false --do_eval=true\\r\\n\\r\\nYou can collect some of this information using our environment capture script:\\r\\n\\r\\nhttps://github.com/tensorflow/tensorflow/tree/master/tools/tf_env_collect.sh\\r\\n\\r\\nYou can obtain the TensorFlow version with\\r\\n\\r\\n`python -c \\\"import tensorflow as tf; print(tf.GIT_VERSION, tf.VERSION)\\\"`\\r\\n\\r\\n### Describe the problem\\r\\nBERT 2.0 uses Einsum instead of Dense to implement Linear transformation for inputs.\\r\\nTF can fuse MatMul and Bias in Dense but failed in Einsum because it generated reshape after MatMul and prevented the fusion:\\r\\n![bert](https://user-images.githubusercontent.com/38638514/65825740-30f39480-e2ad-11e9-91f1-8e1e755d1dc5.png)\\r\\n\\r\\nI'm not familiar with TF 2.0 API, was Dense layer removed in 2.0? If so, can we get any solution to enable the fusion again?\\r\\n\\r\\n### Source code / logs\\r\\n\"\n" +
            "  }", GitHubRepoIssueEntity::class.java
)

val tensorflowModelsRepoIssue2: GitHubRepoIssueEntity = Gson().fromJson(
    "{\n" +
            "    \"url\": \"https://api.github.com/repos/tensorflow/models/issues/7610\",\n" +
            "    \"repository_url\": \"https://api.github.com/repos/tensorflow/models\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/tensorflow/models/issues/7610/labels{/name}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/tensorflow/models/issues/7610/comments\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/tensorflow/models/issues/7610/events\",\n" +
            "    \"html_url\": \"https://github.com/tensorflow/models/issues/7610\",\n" +
            "    \"id\": 499712417,\n" +
            "    \"node_id\": \"MDU6SXNzdWU0OTk3MTI0MTc=\",\n" +
            "    \"number\": 7610,\n" +
            "    \"title\": \"fp16 auto mixed precision transformer of multi-gpu runs much slower than expected\",\n" +
            "    \"user\": {\n" +
            "      \"login\": \"venuswu\",\n" +
            "      \"id\": 3830256,\n" +
            "      \"node_id\": \"MDQ6VXNlcjM4MzAyNTY=\",\n" +
            "      \"avatar_url\": \"https://avatars1.githubusercontent.com/u/3830256?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/venuswu\",\n" +
            "      \"html_url\": \"https://github.com/venuswu\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/venuswu/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/venuswu/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/venuswu/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/venuswu/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/venuswu/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/venuswu/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/venuswu/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/venuswu/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/venuswu/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"labels\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"state\": \"open\",\n" +
            "    \"locked\": false,\n" +
            "    \"assignee\": null,\n" +
            "    \"assignees\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"milestone\": null,\n" +
            "    \"comments\": 13,\n" +
            "    \"created_at\": \"2019-09-28T01:52:36Z\",\n" +
            "    \"updated_at\": \"2019-09-30T09:10:55Z\",\n" +
            "    \"closed_at\": null,\n" +
            "    \"author_association\": \"NONE\",\n" +
            "    \"body\": \"I ran fp16 auto mixed precision transformer on a nvlink v100 of 8 gpus. Here is the topology of this host.\\r\\n\\r\\n![1792878227](https://user-images.githubusercontent.com/3830256/65828421-4d9fc480-e2cd-11e9-977c-69b51e44eae4.jpg)\\r\\n\\r\\n\\r\\nBut, for multi-gpu, the fp16 auto mixed precision transformer runs much slower. Here is the comparison as follows.\\r\\n\\r\\ntype | GPU number| batch size | time every 100steps | examples_per_second\\r\\n-- | -- | -- | -- | -- |\\r\\nfp32 \\t | 1 GPU | 4096 | 53s | \\r\\nfp32 \\t | 4 GPU | 4096*4 | 57s | \\r\\nfp32 \\t | 8 GPU | 4096*8 | 58s |\\r\\nfp16-keras  | 1 GPU | 4096 | 28s | 14118.564668\\r\\nfp16-keras  | 4 GPU | 4096*4 | 40s | 42054.469457\\r\\nfp16-keras  | 8 GPU | 4096*8 | 85s | 38424.255973\\r\\nfp16-amp  | 8 GPU | 4096*8 | 35s | 92973\\r\\n\\r\\n\\r\\n\\r\\nDoes the loss-scaling cost much time on multi gpu? It runs nearly the same when a fixed loss-scale is set.\\r\\n@saberkun @reedwm  @zongweiz @nluehr @yuefengz\\r\\n\\r\\n\"\n" +
            "  }", GitHubRepoIssueEntity::class.java
)

val awesomeTensorflowRepo: GitHubRepoEntity = Gson().fromJson(
    "{\n" +
            "      \"id\": 52677592,\n" +
            "      \"node_id\": \"MDEwOlJlcG9zaXRvcnk1MjY3NzU5Mg==\",\n" +
            "      \"name\": \"awesome-tensorflow\",\n" +
            "      \"full_name\": \"jtoy/awesome-tensorflow\",\n" +
            "      \"private\": false,\n" +
            "      \"owner\": {\n" +
            "        \"login\": \"jtoy\",\n" +
            "        \"id\": 14783,\n" +
            "        \"node_id\": \"MDQ6VXNlcjE0Nzgz\",\n" +
            "        \"avatar_url\": \"https://avatars0.githubusercontent.com/u/14783?v=4\",\n" +
            "        \"gravatar_id\": \"\",\n" +
            "        \"url\": \"https://api.github.com/users/jtoy\",\n" +
            "        \"html_url\": \"https://github.com/jtoy\",\n" +
            "        \"followers_url\": \"https://api.github.com/users/jtoy/followers\",\n" +
            "        \"following_url\": \"https://api.github.com/users/jtoy/following{/other_user}\",\n" +
            "        \"gists_url\": \"https://api.github.com/users/jtoy/gists{/gist_id}\",\n" +
            "        \"starred_url\": \"https://api.github.com/users/jtoy/starred{/owner}{/repo}\",\n" +
            "        \"subscriptions_url\": \"https://api.github.com/users/jtoy/subscriptions\",\n" +
            "        \"organizations_url\": \"https://api.github.com/users/jtoy/orgs\",\n" +
            "        \"repos_url\": \"https://api.github.com/users/jtoy/repos\",\n" +
            "        \"events_url\": \"https://api.github.com/users/jtoy/events{/privacy}\",\n" +
            "        \"received_events_url\": \"https://api.github.com/users/jtoy/received_events\",\n" +
            "        \"type\": \"User\",\n" +
            "        \"site_admin\": false\n" +
            "      },\n" +
            "      \"html_url\": \"https://github.com/jtoy/awesome-tensorflow\",\n" +
            "      \"description\": \"TensorFlow - A curated list of dedicated resources http://tensorflow.org\",\n" +
            "      \"fork\": false,\n" +
            "      \"url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow\",\n" +
            "      \"forks_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/forks\",\n" +
            "      \"keys_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/keys{/key_id}\",\n" +
            "      \"collaborators_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/collaborators{/collaborator}\",\n" +
            "      \"teams_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/teams\",\n" +
            "      \"hooks_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/hooks\",\n" +
            "      \"issue_events_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/events{/number}\",\n" +
            "      \"events_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/events\",\n" +
            "      \"assignees_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/assignees{/user}\",\n" +
            "      \"branches_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/branches{/branch}\",\n" +
            "      \"tags_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/tags\",\n" +
            "      \"blobs_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/git/blobs{/sha}\",\n" +
            "      \"git_tags_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/git/tags{/sha}\",\n" +
            "      \"git_refs_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/git/refs{/sha}\",\n" +
            "      \"trees_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/git/trees{/sha}\",\n" +
            "      \"statuses_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/statuses/{sha}\",\n" +
            "      \"languages_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/languages\",\n" +
            "      \"stargazers_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/stargazers\",\n" +
            "      \"contributors_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/contributors\",\n" +
            "      \"subscribers_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/subscribers\",\n" +
            "      \"subscription_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/subscription\",\n" +
            "      \"commits_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/commits{/sha}\",\n" +
            "      \"git_commits_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/git/commits{/sha}\",\n" +
            "      \"comments_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/comments{/number}\",\n" +
            "      \"issue_comment_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/comments{/number}\",\n" +
            "      \"contents_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/contents/{+path}\",\n" +
            "      \"compare_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/compare/{base}...{head}\",\n" +
            "      \"merges_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/merges\",\n" +
            "      \"archive_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/{archive_format}{/ref}\",\n" +
            "      \"downloads_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/downloads\",\n" +
            "      \"issues_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues{/number}\",\n" +
            "      \"pulls_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/pulls{/number}\",\n" +
            "      \"milestones_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/milestones{/number}\",\n" +
            "      \"notifications_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/notifications{?since,all,participating}\",\n" +
            "      \"labels_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/labels{/name}\",\n" +
            "      \"releases_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/releases{/id}\",\n" +
            "      \"deployments_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/deployments\",\n" +
            "      \"created_at\": \"2016-02-27T17:00:27Z\",\n" +
            "      \"updated_at\": \"2019-09-30T03:33:08Z\",\n" +
            "      \"pushed_at\": \"2019-09-25T08:11:34Z\",\n" +
            "      \"git_url\": \"git://github.com/jtoy/awesome-tensorflow.git\",\n" +
            "      \"ssh_url\": \"git@github.com:jtoy/awesome-tensorflow.git\",\n" +
            "      \"clone_url\": \"https://github.com/jtoy/awesome-tensorflow.git\",\n" +
            "      \"svn_url\": \"https://github.com/jtoy/awesome-tensorflow\",\n" +
            "      \"homepage\": \"\",\n" +
            "      \"size\": 246,\n" +
            "      \"stargazers_count\": 15074,\n" +
            "      \"watchers_count\": 15074,\n" +
            "      \"language\": null,\n" +
            "      \"has_issues\": true,\n" +
            "      \"has_projects\": true,\n" +
            "      \"has_downloads\": true,\n" +
            "      \"has_wiki\": true,\n" +
            "      \"has_pages\": false,\n" +
            "      \"forks_count\": 2759,\n" +
            "      \"mirror_url\": null,\n" +
            "      \"archived\": false,\n" +
            "      \"disabled\": false,\n" +
            "      \"open_issues_count\": 23,\n" +
            "      \"license\": {\n" +
            "        \"key\": \"cc0-1.0\",\n" +
            "        \"name\": \"Creative Commons Zero v1.0 Universal\",\n" +
            "        \"spdx_id\": \"CC0-1.0\",\n" +
            "        \"url\": \"https://api.github.com/licenses/cc0-1.0\",\n" +
            "        \"node_id\": \"MDc6TGljZW5zZTY=\"\n" +
            "      },\n" +
            "      \"forks\": 2759,\n" +
            "      \"open_issues\": 23,\n" +
            "      \"watchers\": 15074,\n" +
            "      \"default_branch\": \"master\",\n" +
            "      \"score\": 97.59109\n" +
            "    }", GitHubRepoEntity::class.java
)

val awesomeTensorflowRepoIssue1: GitHubRepoIssueEntity = Gson().fromJson(
    "{\n" +
            "    \"url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/161\",\n" +
            "    \"repository_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/161/labels{/name}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/161/comments\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/161/events\",\n" +
            "    \"html_url\": \"https://github.com/jtoy/awesome-tensorflow/pull/161\",\n" +
            "    \"id\": 435100357,\n" +
            "    \"node_id\": \"MDExOlB1bGxSZXF1ZXN0MjcxOTQyMzkx\",\n" +
            "    \"number\": 161,\n" +
            "    \"title\": \"Intro to tensorflow offered by Coursera\",\n" +
            "    \"user\": {\n" +
            "      \"login\": \"ricsirigu\",\n" +
            "      \"id\": 5004093,\n" +
            "      \"node_id\": \"MDQ6VXNlcjUwMDQwOTM=\",\n" +
            "      \"avatar_url\": \"https://avatars3.githubusercontent.com/u/5004093?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/ricsirigu\",\n" +
            "      \"html_url\": \"https://github.com/ricsirigu\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/ricsirigu/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/ricsirigu/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/ricsirigu/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/ricsirigu/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/ricsirigu/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/ricsirigu/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/ricsirigu/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/ricsirigu/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/ricsirigu/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"labels\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"state\": \"open\",\n" +
            "    \"locked\": false,\n" +
            "    \"assignee\": null,\n" +
            "    \"assignees\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"milestone\": null,\n" +
            "    \"comments\": 1,\n" +
            "    \"created_at\": \"2019-04-19T08:44:18Z\",\n" +
            "    \"updated_at\": \"2019-09-25T08:11:33Z\",\n" +
            "    \"closed_at\": null,\n" +
            "    \"author_association\": \"CONTRIBUTOR\",\n" +
            "    \"pull_request\": {\n" +
            "      \"url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/pulls/161\",\n" +
            "      \"html_url\": \"https://github.com/jtoy/awesome-tensorflow/pull/161\",\n" +
            "      \"diff_url\": \"https://github.com/jtoy/awesome-tensorflow/pull/161.diff\",\n" +
            "      \"patch_url\": \"https://github.com/jtoy/awesome-tensorflow/pull/161.patch\"\n" +
            "    },\n" +
            "    \"body\": \"\"\n" +
            "  }", GitHubRepoIssueEntity::class.java
)

val awesomeTensorflowRepoIssue2: GitHubRepoIssueEntity = Gson().fromJson(
    "{\n" +
            "    \"url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/158\",\n" +
            "    \"repository_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/158/labels{/name}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/158/comments\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/issues/158/events\",\n" +
            "    \"html_url\": \"https://github.com/jtoy/awesome-tensorflow/pull/158\",\n" +
            "    \"id\": 420109804,\n" +
            "    \"node_id\": \"MDExOlB1bGxSZXF1ZXN0MjYwNDYwNTcw\",\n" +
            "    \"number\": 158,\n" +
            "    \"title\": \"Added Deep Learning Crash Course to Tutorials list\",\n" +
            "    \"user\": {\n" +
            "      \"login\": \"von-latinski\",\n" +
            "      \"id\": 47537191,\n" +
            "      \"node_id\": \"MDQ6VXNlcjQ3NTM3MTkx\",\n" +
            "      \"avatar_url\": \"https://avatars0.githubusercontent.com/u/47537191?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/von-latinski\",\n" +
            "      \"html_url\": \"https://github.com/von-latinski\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/von-latinski/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/von-latinski/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/von-latinski/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/von-latinski/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/von-latinski/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/von-latinski/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/von-latinski/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/von-latinski/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/von-latinski/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"labels\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"state\": \"open\",\n" +
            "    \"locked\": false,\n" +
            "    \"assignee\": null,\n" +
            "    \"assignees\": [\n" +
            "\n" +
            "    ],\n" +
            "    \"milestone\": null,\n" +
            "    \"comments\": 0,\n" +
            "    \"created_at\": \"2019-03-12T17:18:53Z\",\n" +
            "    \"updated_at\": \"2019-06-25T11:32:19Z\",\n" +
            "    \"closed_at\": null,\n" +
            "    \"author_association\": \"NONE\",\n" +
            "    \"pull_request\": {\n" +
            "      \"url\": \"https://api.github.com/repos/jtoy/awesome-tensorflow/pulls/158\",\n" +
            "      \"html_url\": \"https://github.com/jtoy/awesome-tensorflow/pull/158\",\n" +
            "      \"diff_url\": \"https://github.com/jtoy/awesome-tensorflow/pull/158.diff\",\n" +
            "      \"patch_url\": \"https://github.com/jtoy/awesome-tensorflow/pull/158.patch\"\n" +
            "    }", GitHubRepoIssueEntity::class.java
)