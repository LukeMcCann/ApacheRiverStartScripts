testClass=LoadProxyClassesClassNotFoundException
testCategories=loader,loader_spec
testPolicyfile=loader.policy
loader.isHttp=false
loader.requireDlPerm=true
loader.annotator=true
testjvmargs=-Dnet.jini.loader.ClassLoading.provider=org.apache.river.test.spec.loader.util.QATestPreferredClassProvider
org.apache.river.qa.harness.runkitserver=false 
org.apache.river.qa.harness.runjiniserver=false
