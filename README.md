# ReLabs

ReLabs is an unofficial, FOSS client for [XDA (a.k.a XDA Developers)](https://forum.xda-developers.com/) written in Kotlin
and JetPack Compose and is licensed under Apache2.0.

## Development

XDA underwent couple of major migrations during 2021-2022 that changed how the website and forums behaved (references below).

- [Big Changes To XDA Forums are Here](https://www.xda-developers.com/big-changes-to-xda-forums-are-coming-soon/)
- [Welcome to XDA 2021!](https://forum.xda-developers.com/t/closed-welcome-to-xda-2021.4197018/)
- [Welcome to the new XDA!](https://www.xda-developers.com/welcome-to-the-new-xda/)

The new XDA Forums is now based upon [XenForo](https://xenforo.com/) which has a [public REST API documentation](https://xenforo.com/community/pages/api-endpoints/).
This migration was carried out by [Audentio](https://www.audent.io) who implemented tons of custom API endpoints as well as a completely new
OAuth2 login support that is not part of XenForo ([Reference](https://www.audent.io/case-study/xda/full)) and this is not publicly documented.

This app uses OAuth2 endpoints and parameters extracted from the official app to allow users to login and uses the XenForo API documentation
to allow them to interact with XDA Forums. Custom API endpoints will be used where possible.

Support for [XDA Portal](https://www.xda-developers.com/) is also present using the RSS Feed that was made public in one of the reference links above.
