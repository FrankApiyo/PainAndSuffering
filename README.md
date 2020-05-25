This is a repo I created to demonstrate the suffering I have gone through trying to get a file to upload to my backend.

I have given up and I'm going to use django for my backend(for now, this is not something I'd like obviously....)

The first Issue I get is "invalid csrf token". I have followed this tutorial to try to fix this  https://github.com/edbond/CSRF and tried many more other things...

The other is that, when I remove the wrap-defaults middleware, then the invalid csrf-token issue goes away; problem with this is that now, the server does not require csrf token which is insecure. on the other hand it seems the file uploaded is not sent to the server... Please help me debug this or show me some other documentation I can read...

I have gone through the following documentation up to this moment.... 

1. https://luminusweb.com/docs/routes.html#handling_file_uploads
2. https://github.com/ring-clojure/ring/wiki/File-Uploads
3. Examples on the reitit repo