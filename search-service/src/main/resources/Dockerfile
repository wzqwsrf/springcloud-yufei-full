FROM elasticsearch:8.12.2

# 安装 IK 中文分词插件
RUN elasticsearch-plugin install \
    https://get.infini.cloud/elasticsearch/analysis-ik/8.12.2 \
    || true
